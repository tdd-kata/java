/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class App {
    private static final String HOST = "YOUR_HOST";
    private static final String USERNAME = "YOUR_USERNAME";
    private static final String PRIVATE_KEY_FILE = "YOUR_PRIVATE_KEY_FILE";

    public static void main(String[] args) {

        try {
            // JSch 인스턴스 생성
            JSch jsch = new JSch();

            // 세션 객체 생성
            jsch.addIdentity(PRIVATE_KEY_FILE);
            Session session = jsch.getSession(USERNAME, HOST, 22);

            // 호스트 키 검증 무시 (보안에 취약하므로 실제 환경에서는 조심해야 함)
            session.setConfig("StrictHostKeyChecking", "no");
            // session.setConfig("PAMAuthenitcationViaKDBInt", "no");

            // 세션 연결
            session.connect();
            if (session.isConnected()) {
                System.out.println("Connection to Session server is successfully");
            }

            // 실행 채널 열기
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("ls -al");

            // 실행 결과를 받기 위한 스트림 연결
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();

            // 출력 스트림에서 데이터 읽기
            java.io.InputStream input = channel.getInputStream();
            byte[] tmp = new byte[1024];
            while (true) {
                while (input.available() > 0) {
                    int i = input.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (input.available() > 0) continue;
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                Thread.sleep(1000);
            }

            // 채널 및 세션 종료
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
