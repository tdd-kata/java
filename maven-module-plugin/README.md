# 멀티 모듈 프로젝트에서 Maven 플러그인 적용하기

## 목표

프로젝트를 모듈화하고 CI 프로세스에 도입하면 좋을 만한 플러그인들을 적용하고 테스트한다. 

## 참조

- [Maven Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- 정적 분석 도구로 [PMD와 SpotBugs](https://stackoverflow.com/a/4297378) 둘 다 사용하자.
  PMD는 소스 코드를 분석하고, SpotBugs는 바이트코드를 분석한다.
  서로 겹치지 않는 것들이 있기 때문에 모두 사용하는 것이 좋다.
- [Checkstyle](https://checkstyle.sourceforge.io/checks.html) 은 팀의 코드 스타일을 맞출 수 있게 도와준다.
