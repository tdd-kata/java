# JVM

- [JVM](#jvm)
  - [개요](#개요)
  - [JVM의 특징](#jvm의-특징)
  - [자바 컴파일러](#자바-컴파일러)
  - [JVM 컴포넌트](#jvm-컴포넌트)
    - [클래스 로더 (Class Loader)](#클래스-로더-class-loader)
      - [특징](#특징)
      - [클래스 로더 종류](#클래스-로더-종류)
      - [로드 과정](#로드-과정)
    - [런타임 데이터 영역 (Runtime Data Areas)](#런타임-데이터-영역-runtime-data-areas)
      - [PC 레지스터](#pc-레지스터)
      - [JVM 스택](#jvm-스택)
      - [네이티브 메서드 스택(Native Method Stack)](#네이티브-메서드-스택native-method-stack)
      - [힙(Heap)](#힙heap)
      - [메서드 영역(Method Area)](#메서드-영역method-area)
    - [실행 엔진 (Execution Engine)](#실행-엔진-execution-engine)
      - [인터프리터 (Interpreter)](#인터프리터-interpreter)
      - [JIT(Just-In-Time) 컴파일러](#jitjust-in-time-컴파일러)
      - [가비지 컬렉터 (GC, Garbage Collector)](#가비지-컬렉터-gc-garbage-collector)
  - [Hands on](#hands-on)
    - [sdkman 설치](#sdkman-설치)
    - [`jcmd`](#jcmd)
    - [`java`](#java)
  - [참고 자료](#참고-자료)

## 개요

![jvm-architecture.png](images/jvm-architecture.png)

_출처: [The JVM Architecture Explained](https://dzone.com/articles/jvm-architecture-explained) - DZone_

가상 머신(Virtual Machine)이란 물리 머신의 논리적 구현체라 할 수 있습니다.
JVM(Java Virtual Machine)은 자바 바이트코드를 해석하고 실행하는 가상 머신입니다.
자바는 원래 WORA(Write Once Run Anywhere)를 구현하기 위해 물리적인 머신과 별개의 가상 머신을 기반으로 동작하도록 설계되었습니다.
클래스 로더(Class Loader)가 컴파일된 자바 바이트코드(class file)를 런타임 데이터 영역(Runtime Data Areas)에 로드하고,
실행 엔진(Execution Engine)이 자바 바이트코드를 실행합니다.

## JVM의 특징

- 스택 기반의 가상 머신
  - 대표적인 컴퓨터 아키텍처인 인텔 x86 아키텍처나 ARM 아키텍처와 같은 하드웨어는 레지스터 기반으로 동작합니다.
  - 그에 비해 JVM은 스택 기반으로 동작합니다.
- 심볼릭 레퍼런스
  - 기본 자료형(primitive data type)을 제외한 모든 타입(클래스와 인터페이스)을 명시적인 메모리 주소 기반의 레퍼런스가 아니라 심볼릭 레퍼런스를 통해 참조한다.
  - 심볼릭 레퍼런스?
- 가비지 컬렉션(garbage collection)
  - 클래스 인스턴스는 사용자 코드에 의해 명시적으로 생성되고 가비지 컬렉션에 의해 자동으로 파괴된다.
- 기본 자료형을 명확하게 정의하여 플랫폼 독립성 보장
  - C/C++ 등의 전통적인 언어는 플랫폼에 따라 int 형의 크기가 변한다.
  - JVM은 기본 자료형을 명확하게 정의하여 호환성을 유지하고 플랫폼 독립성을 보장한다.
- 네트워크 바이트 오더(network byte order)
  - 자바 클래스 파일은 네트워크 바이트 오더를 사용한다.
  - 인텔 x86 아키텍처가 사용하는 리틀 엔디안이나, RISC 계열 아키텍처가 주로 사용하는 빅 엔디안 사이에서 플랫폼 독립성을 유지하려면 고정된 바이트 오더를 유지해야 하므로 네트워크 전송 시에 사용하는 바이트 오더인 네트워크 바이트 오더를 사용한다.
  - 네트워크 바이트 오더는 빅 엔디안이다.

## 자바 컴파일러

- 자바 컴파일러는 JVM의 요소가 아닙니다.
- 자바 컴파일러는 C/C++ 등의 컴파일러처럼 고수준 언어를 기계어, 즉 직접적인 CPU 명령으로 변환하는 것이 아니라, 개발자가 이해하는 자바 언어를 JVM이 이해하는 자바 바이트코드로 번역한다.

## JVM 컴포넌트

### 클래스 로더 (Class Loader)

자바는 동적 로드, 즉 컴파일타임이 아니라 런타임에 클래스를 처음으로 참조할 때 해당 클래스를 로드하고 링크하는 특징이 있습니다.
이 동적 로드를 담당하는 부분이 JVM의 클래스 로더입니다.

클래스 로더가 클래스 로드를 요청받으면, 클래스 로더 캐시, 상위 클래스 로더, 자기 자신의 순서로 해당 클래스가 있는지 확인합니다.
즉, 이전에 로드된 클래스인지 클래스 로더 캐시를 확인하고, 없으면 상위 클래스 로더를 거슬러 올라가며 확인합니다.
부트스트랩 클래스 로더까지 확인해도 없으면 요청받은 클래스 로더가 파일 시스템에서 해당 클래스를 찾습니다.

#### 특징

- 계층 구조
  - 클래스 로더끼리 부모-자식 관계를 이루어 계층 구조로 생성됩니다. 최상위 클래스 로더는 부트스트랩 클래스 로더(Bootstrap Class Loader)입니다.
- 위임 모델
  - 계층 구조를 바탕으로 클래스 로더끼리 로드를 위임하는 구조로 동작합니다. 클래스를 로드할 때 먼저 상위 클래스 로더를 확인하여 상위 클래스 로더에 있다면 해당 클래스를 사용하고, 없다면 로드를 요청받은 클래스 로더가 클래스를 로드합니다.
- 가시성(visibility) 제한
  - 하위 클래스 로더는 상위 클래스 로더의 클래스를 찾을 수 있지만, 상위 클래스 로더는 하위 클래스 로더의 클래스를 찾을 수 없습니다.
- 언로드 불가
  - 클래스 로더는 클래스를 로드할 수는 있지만 언로드할 수는 없습니다. 언로드 대신, 현재 클래스 로더를 삭제하고 아예 새로운 클래스 로더를 생성하는 방법을 사용할 수 있습니다.

#### 클래스 로더 종류

- 부트스트랩 클래스 로더(Bootstrap Extension Class Loader)
  - JVM을 기동할 때 생성되며, Object 클래스들을 비롯하여 자바 API들을 로드합니다.
  - 다른 클래스 로더와 달리 자바가 아니라 네이티브 코드로 구현되어 있습니다.
- 확장 클래스 로더(Extension Class Loader)
  - 기본 자바 API를 제외한 확장 클래스들을 로드합니다.
  - 다양한 보안 확장 기능 등을 여기에서 로드하게 됩니다.
  - jre/lib/ext 폴더나 java.ext.dirs 환경변수에 지정된 경로를 참조합니다.
- 시스템 클래스 로더(System Class Loader)
  - 부트스트랩 클래스 로더와 익스텐션 클래스 로더가 JVM 자체의 구성 요소들을 로드하는 것이라 한다면, 시스템 클래스 로더는 애플리케이션의 클래스들을 로드한다고 할 수 있습니다.
  - 사용자가 지정한 $CLASSPATH 내의 클래스들을 로드합니다.
- 사용자 정의 클래스 로더(User-Defined Class Loader)
  - 애플리케이션 사용자가 직접 코드 상에서 생성해서 사용하는 클래스 로더입니다.

#### 로드 과정

- 로드: 클래스를 파일에서 가져와서 JVM의 메모리에 로드합니다.
- 검증(Verifying): 읽어 들인 클래스가 자바 언어 명세(Java Language Specification) 및 JVM 명세에 명시된 대로 잘 구성되어 있는지 검사합니다. 클래스 로드의 전 과정 중에서 가장 까다로운 검사를 수행하는 과정으로서 가장 복잡하고 시간이 많이 걸립니다. JVM TCK의 테스트 케이스 중에서 가장 많은 부분이 잘못된 클래스를 로드하여 정상적으로 검증 오류를 발생시키는지 테스트하는 부분입니다.
- 준비(Preparing): 클래스가 필요로 하는 메모리를 할당하고, 클래스에서 정의된 필드, 메서드, 인터페이스들을 나타내는 데이터 구조를 준비합니다.
- 분석(Resolving): 클래스의 상수 풀 내 모든 심볼릭 레퍼런스를 다이렉트 레퍼런스로 변경합니다.
- 초기화: 클래스 변수들을 적절한 값으로 초기화합니다. 즉, static initializer들을 수행하고, static 필드들을 설정된 값으로 초기화합니다.

### 런타임 데이터 영역 (Runtime Data Areas)

![jvm-runtime-data-areas.png](images/jvm-runtime-data-areas.png)

![jvm-stack-per-thread.png](images/jvm-stack-per-thread.png)

*출처: [JVM Internal](https://d2.naver.com/helloworld/1230) - Naver D2*

JVM이라는 프로그램이 운영체제 위에서 실행되면서 할당받는 메모리 영역입니다.
런타임 데이터 영역은 6개의 영역으로 나눌 수 있습니다.
PC 레지스터(PC Register), JVM 스택(JVM Stack), 네이티브 메서드 스택(Native Method Stack)은 스레드마다 하나씩 생성되며
힙(Heap), 메서드 영역(Method Area), 런타임 상수 풀(Runtime Constant Pool)은 모든 스레드가 공유해서 사용합니다.

#### PC 레지스터

- PC(Program Counter) 레지스터는 각 스레드마다 하나씩 존재하며 스레드가 시작될 때 생성됩니다.
- PC 레지스터는 현재 수행 중인 JVM 명령의 주소를 갖습니다.

#### JVM 스택

- JVM 스택은 각 스레드마다 하나씩 존재하며 스레드가 시작될 때 생성됩니다.
- 스택 프레임(Stack Frame)이라는 구조체를 저장하는 스택으로, JVM은 오직 JVM 스택에 스택 프레임을 추가하고(push) 제거하는(pop) 동작만 수행합니다.
- 예외 발생 시 printStackTrace() 등의 메서드로 보여주는 Stack Trace의 각 라인은 하나의 스택 프레임을 표현합니다.
- 스택 프레임(Stack Frame)
  - JVM 내에서 메서드가 수행될 때마다 하나의 스택 프레임이 생성되어 해당 스레드의 JVM 스택에 추가되고 메서드가 종료되면 스택 프레임이 제거됩니다. 각 스택 프레임은 지역 변수 배열(Local Variable Array), 피연산자 스택(Operand Stack), 현재 실행 중인 메서드가 속한 클래스의 런타임 상수 풀에 대한 레퍼런스를 갖습니다.
  - 지역 변수 배열, 피연산자 스택의 크기는 컴파일 시에 결정되기 때문에 스택 프레임의 크기도 메서드에 따라 크기가 고정됩니다.
  - 지역 변수 배열(Local Variable Array)
    - 0부터 시작하는 인덱스를 가진 배열입니다. 0은 메서드가 속한 클래스 인스턴스의 this 레퍼런스이고, 1부터는 메서드에 전달된 파라미터들이 저장되며, 메서드 파라미터 이후에는 메서드의 지역 변수들이 저장됩니다.
  - 피연산자 스택(Operand Stack)
    - 메서드의 실제 작업 공간입니다. 각 메서드는 피연산자 스택과 지역 변수 배열 사이에서 데이터를 교환하고, 다른 메서드 호출 결과를 추가하거나(push) 꺼냅니다(pop). 피연산자 스택 공간이 얼마나 필요한지는 컴파일할 때 결정할 수 있으므로, 피연산자 스택의 크기도 컴파일 시에 결정됩니다.

#### 네이티브 메서드 스택(Native Method Stack)

- 자바 외의 언어로 작성된 네이티브 코드를 위한 스택입니다.
- 즉, JNI(Java Native Interface)를 통해 호출하는 C/C++ 등의 코드를 수행하기 위한 스택으로, 언어에 맞게 C 스택이나 C++ 스택이 생성됩니다.

#### 힙(Heap)

- 인스턴스 또는 객체를 저장하는 공간으로 **가비지 컬렉션 대상**입니다.
- JVM 성능 등의 이슈에서 가장 많이 언급되는 공간입니다.
- 힙 구성 방식이나 가비지 컬렉션 방법 등은 JVM 벤더의 재량입니다.
- 하나의 물리 머신에서 여러 개의 Java 애플리케이션이 실행 중이라면 각 애플리케이션마다 `java` 명령어를 통해 JVM이 실행 중이라는 뜻이고 힙 영역 또한 각 JVM마다 하나씩 독립적으로 존재합니다.

#### 메서드 영역(Method Area)

- 메서드 영역은 모든 스레드가 공유하는 영역으로 JVM이 시작될 때 생성됩니다.
- JVM이 읽어 들인 각각의 클래스와 인터페이스에 대한 런타임 상수 풀, 필드와 메서드 정보, static 변수, 메서드의 바이트코드 등을 보관합니다. 메서드 영역은 JVM 벤더마다 다양한 형태로 구현할 수 있으며, 오라클 핫스팟 JVM(HotSpot JVM)에서는 흔히 Permanent Area, 혹은 Permanent Generation(PermGen)이라고 불립니다.
- 메서드 영역에 대한 가비지 컬렉션은 JVM 벤더의 선택 사항입니다.
- 런타임 상수 풀 (Runtime Constant Pool)
  - 클래스 파일 포맷에서 constant_pool 테이블에 해당하는 영역입니다.
  - 메서드 영역에 포함되는 영역이긴 하지만, JVM 동작에서 가장 핵심적인 역할을 수행하는 곳이기 때문에 JVM 명세에서도 따로 중요하게 기술합니다. 각 클래스와 인터페이스의 상수뿐만 아니라, 메서드와 필드에 대한 모든 레퍼런스까지 담고 있는 테이블입니다. 즉, 어떤 메서드나 필드를 참조할 때 JVM은 런타임 상수 풀을 통해 해당 메서드나 필드의 실제 메모리상 주소를 찾아서 참조합니다.

### 실행 엔진 (Execution Engine)

클래스 로더를 통해 JVM 내의 런타임 데이터 영역에 배치된 바이트코드는 실행 엔진에 의해 실행됩니다.
실행 엔진은 자바 바이트코드를 명령어 단위로 읽어서 실행합니다.
CPU가 기계 명령어(ISA)를 하나씩 실행하는 것과 비슷합니다.
그런데 자바 바이트코드는 기계가 바로 수행할 수 있는 언어보다는 비교적 인간이 보기 편한 형태로 기술된 것입니다.
그래서 실행 엔진은 이와 같은 바이트코드를 실제로 JVM 내부에서
인터프리터 방식이나 JIT 컴파일러 방식으로 기계가 실행할 수 있는 형태로 변경합니다.

#### 인터프리터 (Interpreter)

- 바이트코드 명령어를 하나씩 읽어서 해석하고 실행합니다.
- 하나씩 해석하고 실행하기 때문에 바이트코드 하나하나의 해석은 빠른 대신 인터프리팅 결과의 실행은 느리다는 단점을 가지고 있습니다. 흔히 얘기하는 인터프리터 언어의 단점을 그대로 가지는 것입니다.
- 즉, 바이트코드라는 '언어'는 기본적으로 인터프리터 방식으로 동작합니다.

#### JIT(Just-In-Time) 컴파일러

- 인터프리터의 단점을 보완하기 위해 도입된 것이 JIT 컴파일러입니다.
- 인터프리터 방식으로 실행하다가 적절한 시점에 바이트코드 전체를 컴파일하여 네이티브 코드로 변경하고, 이후에는 해당 메서드를 더 이상 인터프리팅하지 않고 네이티브 코드로 직접 실행하는 방식입니다.
- 네이티브 코드를 실행하는 것이 하나씩 인터프리팅하는 것보다 빠르고, 네이티브 코드는 캐시에 보관하기 때문에 한 번 컴파일된 코드는 계속 빠르게 수행되게 됩니다.

#### 가비지 컬렉터 (GC, Garbage Collector)

힙의 클래스 인스턴스는 사용자 코드에 의해 명시적으로 생성되고 가비지 컬렉션에 의해 자동으로 파괴됩니다.
[더 보기](garbage-collector.md)

## Hands on

### [sdkman](https://sdkman.io) 설치

```bash
curl -s "https://get.sdkman.io" | bash
# fish에선 올바르게 동작하지 않습니다.
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
# SDKMAN 5.11.0+644
```

```bash
sdk help
sdk list java
sdk install java 11.0.11-open
```

```bash
java -version
# openjdk 11.0.11 2021-04-20
# OpenJDK Runtime Environment 18.9 (build 11.0.11+9)
# OpenJDK 64-Bit Server VM 18.9 (build 11.0.11+9, mixed mode)
```

### `jcmd`

```bash
# Java 애플리케이션의 PID를 조회합니다.
jps
# 실행 중인 JVM에 진단 명령 요청을 합니다.
jcmd ${JAVA_APP_PID} VM.flags | grep GC
```

### `java`

```bash
java -XX:+PrintCommandLineFlags -version
# [...] -XX:+UseG1GC
```

## 참고 자료

- [JVM Internal](https://d2.naver.com/helloworld/1230) - Naver D2
- [자바 최적화 (Optimizing Java)](https://www.aladin.co.kr/shop/wproduct.aspx?isbn=9791162241776) - 벤저민 J. 에번스, 제임스 고프, 크리스 뉴랜드
- [OpenJDK source code](https://github.com/openjdk/jdk)
  - [physical stack frame](https://github.com/openjdk/jdk/blob/jdk-11%2B28/src/hotspot/share/runtime/frame.hpp#L50)
  - [heap region type](https://github.com/openjdk/jdk/blob/jdk-11%2B28/src/hotspot/share/gc/g1/heapRegionType.hpp#L33)
