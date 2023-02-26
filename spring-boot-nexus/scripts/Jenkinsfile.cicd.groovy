// https://plugins.jenkins.io/workflow-aggregator/
// https://plugins.jenkins.io/slack/
// https://plugins.jenkins.io/bitbucket/
// https://plugins.jenkins.io/jira-steps/
timestamps {

    // Slack
    SLACK_CHANNEL = '#jenkins'
    SLACK_TEAM_DOMAIN = 'slack-workspace' // workspace
    SLACK_CREDENTIAL_ID = 'jenkins-slack-credential'// Credential ID

    // SSH
    SSH_CREDENTIAL_ID = 'ssh-credential'
    SSH_USER_ID = 'ssh-user'

    // remote server
    REMOTE_SERVER_HOST = "111.222.111.222"
    APP_PORT = '23000'
    APP_PROFILE = 'dev'
    HEALTHCHECK_SCRIPT = '/home/markruler/bin/healthcheck'

    // Artifact
    BUILD_PATH = 'apiserver/build/libs'
    ARTIFACT_FILTER_REGEX = /\.jar$/
    ARTIFACT_PATH = '/home/markruler/artifacts'

    // Git
    GIT_CREDENTIAL_ID = 'git-credential'
    GIT_URL = 'git@github.com:markruler/apiserver.git'
    GIT_BRANCH = 'develop'

    node {
        List changes = []

        try {

            stage('Checkout') {
                // https://www.jenkins.io/doc/pipeline/steps/workflow-scm-step/
                // https://plugins.jenkins.io/git/#plugin-content-pipeline-examples
                deleteDir()

                checkout(
                        [
                                $class                           : 'GitSCM',
                                branches                         : [[name: GIT_BRANCH]],
                                doGenerateSubmoduleConfigurations: false,
                                extensions                       : [],
                                submoduleCfg                     : [],
                                userRemoteConfigs                : [
                                        [
                                                credentialsId: GIT_CREDENTIAL_ID,
                                                url          : GIT_URL
                                        ]
                                ]
                        ]
                )
            }

            stage('Link Jira') {
                // https://jenkinsci.github.io/jira-steps-plugin/steps/issue/jira_get_issue/
                // https://www.jenkins.io/doc/pipeline/steps/jira-steps/
                script {
                    echo "---------------- git_change_set ----------------"
                    // java.util.LinkedHashSet
                    Set jira_key = list_jira_key_from_changelogs()

                    echo "---------------- jira_issue_set ----------------"
                    changes = list_change_sets_from_jira_issues(jira_key)
                }
            }

            withEnv(["JAVA_HOME=${tool 'java 17'}", "PATH=${tool 'java 17'}/bin:${env.PATH}"]) {
                stage('Build') {
                    sh "./gradlew -i clean :apiserver:build"
                }
            }

            withCredentials([usernamePassword(credentialsId: SSH_CREDENTIAL_ID, passwordVariable: 'password', usernameVariable: 'userName')]) {

                def remote1 = [:]
                remote1.name = SSH_USER_ID
                remote1.host = REMOTE_SERVER_HOST
                remote1.allowAnyHosts = true
                remote1.user = userName
                remote1.password = password

                stage('Stop server') {
                    sshCommand remote: remote1, command: """
                        pid=\$(netstat -npl 2>/dev/null | awk '/:${APP_PORT} */ {split(\$NF,a,"/"); print a[1]}');
                        echo "============ before application pid: \$pid ========================";
                        kill -TERM \$pid || kill -KILL \$pid;
                        echo "============ kill pid: \$pid ========================";
                    """
                }

                stage('Copy Artifact') {
                    sshCommand remote: remote1, command: "rm -rf ${ARTIFACT_PATH}/libs/apiserver.jar"
                    sshPut remote: remote1, from: BUILD_PATH, filterRegex: ARTIFACT_FILTER_REGEX, into: ARTIFACT_PATH
                }

                stage('Deploy to development') {
                    sshCommand remote: remote1, command: "nohup java -jar ${ARTIFACT_PATH}/libs/*.jar --spring.profiles.active=${APP_PROFILE} > /dev/null &"
                }

                stage('Health Check') {
                    sshCommand remote: remote1, command: "${HEALTHCHECK_SCRIPT} ${REMOTE_SERVER_HOST}:${APP_PORT}/actuator/health"
                }
            }

            stage('Notification') {
                // https://plugins.jenkins.io/ws-cleanup/
                // https://www.jenkins.io/doc/pipeline/steps/ws-cleanup/
                // cleanWs(deleteDirs: true)

                if (!changes.isEmpty()) {
                    // https://github.com/markruler/naver-works-plugin
                    naver(
                            credentialId: env.NAVER_WORKS_CREDENTIAL_ID,
                            botId: env.NAVER_WORKS_BOT_ID,
                            channelId: env.NAVER_WORKS_DEV_CHANNEL_ID,
                            messageType: 'list_template',
                            backgroundImageUrl: env.NAVER_WORKS_BG_URL,
                            contentActionLabel: env.NAVER_WORKS_CONTENT_LABEL,
                            contentActionLink: env.BUILD_URL,
                            messages: changes
                    )
                } else {
                    naver(
                            credentialId: env.NAVER_WORKS_CREDENTIAL_ID,
                            botId: env.NAVER_WORKS_BOT_ID,
                            channelId: env.NAVER_WORKS_DEV_CHANNEL_ID,
                            messageType: 'text',
                            simpleMessage: '변경 사항이 개발 환경에 반영되었습니다.'
                    )
                }

                // https://www.jenkins.io/doc/pipeline/steps/slack/
                def change_logs = make_change_logs(changes)

                slackSend(
                        color: '#2EB886',
                        message: """SUCCESSFUL:
                                |Jenkins Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})
                                |${change_logs}
                                |""".stripMargin(),
                        teamDomain: SLACK_TEAM_DOMAIN,
                        channel: SLACK_CHANNEL,
                        tokenCredentialId: SLACK_CREDENTIAL_ID
                )
            }
        }
        catch (err) {
            // cleanWs(deleteDirs: true)

            def change_logs = make_change_logs(changes)

            slackSend(
                    color: '#FF0000',
                    message: """FAILED:
                            |Jenkins Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})
                            |${change_logs}
                            |""".stripMargin(),
                    teamDomain: SLACK_TEAM_DOMAIN,
                    channel: SLACK_CHANNEL,
                    tokenCredentialId: SLACK_CREDENTIAL_ID
            )

            throw err
        }
    }
}

def make_change_logs(List change_list) {

    if (change_list.isEmpty()) {
        return ""
    }

    def change_logs = ""

    try {
        change_logs += "Changelog:\n"
        change_list.each { issue ->
            change_logs += "[<$issue.link|$issue.title>] $issue.subtitle\n"
        }
    } catch (e) {
        echo "Exception: ${e} :: ${change_list}"
    }

    return change_logs
}

// expected to call WorkflowScript.getJIRASetFromChangelogSet but wound up catching withEnv; see: https://jenkins.io/redirect/pipeline-cps-method-mismatches/
@NonCPS
def list_jira_key_from_changelogs() {
    Set jiraset = []

    // GitSCM에서 가져오는 ChangeSet은 변경 사항이 없다면 아무것도 없다.
    // 커밋 메시지에 이슈 번호(XXXX-123)가 있으면 이것이 entry의 키값이다.
    // 커밋 메시지에 이슈 번호가 없다면 아무것도 안나온다.
    def changeLogSets = currentBuild.changeSets

    changeLogSets.each { entries ->

        entries.each { entry ->
            // Commit Message
            echo "Commit Message >>> $entry.msg"

            // java.util.regex.Matcher
            def jiramatch = (entry.msg =~ /([a-zA-Z0-9]+-[0-9]+)(.*)/)

            while (jiramatch) {
                jiraset.leftShift(jiramatch.group(1))

                def shorter = jiramatch.group(2)
                jiramatch = (shorter =~ /([a-zA-Z0-9]+-[0-9]+)(.*)/)
            }
        }
    }

    return jiraset
}

def list_change_sets_from_jira_issues(Set jira_keys) {
    List change_list = []

    jira_keys.each { jira_key ->
        echo "jira_key >> $jira_key"
        try {
            def issue = jiraGetIssue idOrKey: jira_key, site: 'myjira'
            // Jira Issue Data
            println issue.data.toString()

            def issue_key = issue.data.key
            def issue_title = issue.data.fields.summary

            change = [link: "https://markruler.atlassian.net/browse/$issue_key", title: issue_key, subtitle: issue_title]
            change_list << change
        } catch (e) {
            echo "Exception: ${e} :: ${jira_key}"

            change = [link: "https://markruler.atlassian.net/browse/$jira_key", title: jira_key, subtitle: "-"]
            change_list << change
        }
    }

    println "change_list.toString() >> " + change_list.toString()

    return change_list
}
