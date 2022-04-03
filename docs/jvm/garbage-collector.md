# 가비지 컬렉터 (Garbage Collector)

- [가비지 컬렉터 (Garbage Collector)](#가비지-컬렉터-garbage-collector)
  - [개요](#개요)
  - [용어](#용어)
    - [Minor GC](#minor-gc)
    - [Full GC](#full-gc)
  - [종류](#종류)
    - [Serial GC (`-XX:+UseSerialGC`)](#serial-gc--xxuseserialgc)
    - [Parallel (Throughput) GC (`-XX:+UseParallelGC`)](#parallel-throughput-gc--xxuseparallelgc)
    - [Parallel Old GC: Parallel Compacting GC](#parallel-old-gc-parallel-compacting-gc)
    - [CMS(Concurrent Mark & Sweep) GC (`-XX:+UseConcMarkSweepGC`)](#cmsconcurrent-mark--sweep-gc--xxuseconcmarksweepgc)
    - [G1(Garbage First) GC (`-XX:+UseG1GC`)](#g1garbage-first-gc--xxuseg1gc)
  - [GC 튜닝 가이드](#gc-튜닝-가이드)
  - [참고 자료](#참고-자료)

## 개요

Heap의 클래스 인스턴스는 사용자 코드에 의해 명시적으로 생성되고 가비지 컬렉션에 의해 자동으로 파괴된다.

## 용어

### Minor GC

Young 제너레이션이 가득 차면 모든 스레드를 중단(stop-the-world)시킨 후 더 이상 사용되지 않는 객체는 폐기시키고 사용 중인 객체는 다른 곳(Old Generation 등)으로 옮긴다.

### Full GC

Old 제너레이션이 가득 차면 모든 스레드를 중단(stop-the-world)시킨 후 더 이상 사용되지 않는 객체를 폐기시킨다.
일반적으로 Minor GC보다 더 오래 걸린다.
즉, 스레드가 더 오래 중단된다.
Full GC를 실행했는데도 새 오브젝트를 저장할 공간이 없다면
`OutOfMemoryError` 를 발생시킨다.

## 종류

### Serial GC (`-XX:+UseSerialGC`)

- 데스크톱의 CPU 코어가 하나만 있을 때 사용하기 위해서 만든 방식이다.
- Serial GC를 사용하면 애플리케이션의 성능이 많이 떨어진다.
- 운영 서버에서 절대 사용하면 안 되는 방식이다.

### Parallel (Throughput) GC (`-XX:+UseParallelGC`)

- JDK 7u4 (JDK 7 Update 4) 이전 버전에서는 `-XX:+UseParallelGC -XX:+UseParallelOldGC`라고 명시해준다.
- 최대 처리량을 위한 GC이며 Java 8의 디폴트 GC다.
- Mark, Sweep, Compact가 동시에 발생하여 상대적으로 CMS보다 중단 시간이 긴 편이다.
- 일반적으로 Young 제너레이션 수집용 수집기다.

### Parallel Old GC: Parallel Compacting GC

- Parallel compaction 기능을 비활성화하려면 `-XX:+UseParallelGC -XX:-UseParallelOldGC`

### CMS(Concurrent Mark & Sweep) GC (`-XX:+UseConcMarkSweepGC`)

- 중단 시간을 아주 짧게 하려고 설계된 테뉴어드(tenured, old) 공간 전용 컬렉터다.
- Parallel GC를 조금 변형한 수집기(ParNew)와 함께 사용된다.
- Full GC시 중단 시간이 상대적으로 Parallel GC보다 짧은 편이다.
- Parallel GC에 비해 처리량이 적은 편이고 더 많은 자원을 사용한다.

### G1(Garbage First) GC (`-XX:+UseG1GC`)

- Java 9부터 디폴트 GC이기 때문에 보통은 따로 옵션을 써서 활성화할 필요가 없다.

## GC 튜닝 가이드

TODO: 정리

- GC 횟수 줄이기
- GC 시간 줄이기

## 참고 자료

- [The Garbage Collection Handbook](https://www.aladin.co.kr/shop/wproduct.aspx?isbn=9788960778238) - 리차드 존스,앤토니 호스킹,엘리엇 모스
- [자바 성능 튜닝 이야기](https://www.aladin.co.kr/shop/wproduct.aspx?isbn=9788966260928) - 이상민
- [Java Garbage Collection](https://d2.naver.com/helloworld/1329) - Naver D2
- [Garbage Collection 튜닝](https://d2.naver.com/helloworld/37111) - Naver D2
- [GC 정책 중 UseParallelGC와 UseParallelOldGC에 대한 고찰](https://sarc.io/index.php/java/478-gc-useparallelgc-useparalleloldgc) - 삵
- [Java HotSpot VM G1GC](https://johngrib.github.io/wiki/java-g1gc/)
- Hotspot Virtual Machine Garbage Collection Tuning Guide
  - [HTG-12](https://docs.oracle.com/en/java/javase/12/gctuning/garbage-first-garbage-collector.html)
  - [HTG-11](https://docs.oracle.com/en/java/javase/11/gctuning/garbage-first-garbage-collector.html)
  - [HTG-10](https://docs.oracle.com/javase/10/gctuning/garbage-first-garbage-collector.htm)
  - [HTG-9](https://docs.oracle.com/javase/9/gctuning/garbage-first-garbage-collector.htm)
  - [HTG-8](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/g1_gc.html)
