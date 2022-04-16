package org.xpdojo.spring.beanlifecycle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <code>@PostConstruct</code>, <code>@PreDestroy</code> 애노테이션을 사용하자.
 * 코드를 고칠 수 없는 외부 라이브러리를 초기화, 종료해야 한다면 <code>@Bean</code>의
 * <code>initMethod</code>, <code>destroyMethod</code>를 사용한다.
 */
class BeanLifecycleTest {

    @Test
    void sut_bean_lifecycle_setter() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SetterTestConfig.class);
        NetworkClientSetter networkClient = context.getBean(NetworkClientSetter.class);
        String message = networkClient.send("Hello");
        assertThat(message).isEqualTo("Calling http://localhost:8080 : Hello");
        context.close();
    }

    @Configuration
    static class SetterTestConfig {
        @Bean
        public NetworkClientSetter networkClientSetter() {
            NetworkClientSetter networkClient = new NetworkClientSetter();
            networkClient.setUrl("http://localhost:8080");
            return networkClient;
        }
    }

    @Test
    @DisplayName("InitializingBean#afterPropertiesSet(), DisposableBean#destroy()를 활용한 빈 라이프사이클 관리")
    void sut_bean_lifecycle_interface() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(InterfaceTestConfig.class);
        NetworkClientInterface networkClient = context.getBean(NetworkClientInterface.class);
        String message = networkClient.send("Hello");
        assertThat(message).isEqualTo("Calling http://localhost:8080 : Hello");
        context.close();
    }

    @Configuration
    static class InterfaceTestConfig {
        @Bean
        public NetworkClientInterface networkClientConstructor() {
            return new NetworkClientInterface("http://localhost:8080");
        }
    }

    @Test
    @DisplayName("initMethod, destroyMethod를 활용한 빈 라이프사이클 관리")
    void sut_bean_lifecycle_method() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(MethodTestConfig.class);
        NetworkClientMethod networkClient = context.getBean(NetworkClientMethod.class);
        String message = networkClient.send("Hello");
        assertThat(message).isEqualTo("Calling http://localhost:8080 : Hello");
        context.close();
    }

    @Configuration
    static class MethodTestConfig {

        // 메서드 이름을 자유롭게 사용할 수 있다.
        // 자바 빈이 스프링 프레임워크에 의존하지 않는다.
        // 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에서도 초기화, 종료 메서드를 적용할 수 있다.
        // destroyMethod의 기본값은 (inferred)로 close, shutdown 이름의 메서드를 자동으로 호출한다.
        // DisposableBeanAdapter.CLOSE_METHOD_NAME
        // DisposableBeanAdapter.SHUTDOWN_METHOD_NAME
        @Bean(initMethod = "init", destroyMethod = "(inferred)")
        public NetworkClientMethod networkClientMethod() {
            return new NetworkClientMethod("http://localhost:8080");
        }
    }

    @Test
    @DisplayName("[JSR-250] @PostConstruct, @PreDestroy 애노테이션을 활용한 빈 라이프사이클 관리")
    void sut_bean_lifecycle_annotation() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AnnotationTestConfig.class);
        NetworkClientAnnotation networkClient = context.getBean(NetworkClientAnnotation.class);
        String message = networkClient.send("Hello");
        assertThat(message).isEqualTo("Calling http://localhost:8080 : Hello");
        context.close();
    }

    @Configuration
    static class AnnotationTestConfig {

        // 최신 스프링에서 가장 권장하는 방법이다.
        // 애노테이션 하나만 붙이면 되므로 매우 편리하다.
        // javax.annotation.PostConstruct, javax.annotation.PreDestroy
        // 패키지를 보면 스프링에 종속적인 기술이 아니라 자바 표준(JSR-250)이다.
        // 컴포넌트 스캔과 잘 어울린다.
        // 유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다.
        @Bean
        public NetworkClientAnnotation networkClientAnnotation() {
            return new NetworkClientAnnotation("http://localhost:8080");
        }
    }

}
