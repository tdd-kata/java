package org.xpdojo.spring.beanscope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 싱글톤 빈에 주입된 프로토타입 빈은 스프링 컨테이너에서 관리하지 않기 때문에
 * 프로토타입으로서 제대로 된 역할을 하지 못한다.
 */
class SingletonWithPrototypeTest {

    @Test
    @DisplayName("Singleton bean with prototype bean")
    void singleton_bean_with_prototype_bean() {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ClientBean.class, Prototype.class); // Prototype bean is created

        ClientBean clientBean1 = context.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);
        assertThat(clientBean1.getClass().getDeclaredAnnotation(Scope.class).value())
                .isEqualTo(ConfigurableBeanFactory.SCOPE_SINGLETON);
        assertThat(clientBean1.prototype.getClass().getDeclaredAnnotation(Scope.class).value())
                .isEqualTo(ConfigurableBeanFactory.SCOPE_PROTOTYPE);

        // Prototype인데 Singleton인 ClientBean은 이미 생성된 객체를 사용하므로 새로운 객체를 생성하지 않는다.
        ClientBean clientBean2 = context.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
        assertThat(clientBean2.getClass().getDeclaredAnnotation(Scope.class).value())
                .isEqualTo(ConfigurableBeanFactory.SCOPE_SINGLETON);
        assertThat(clientBean2.prototype.getClass().getDeclaredAnnotation(Scope.class).value())
                .isEqualTo(ConfigurableBeanFactory.SCOPE_PROTOTYPE);

        context.close(); // Singleton bean is destroyed
    }

    @Test
    @DisplayName("ObjectProvider를 사용해서 Singleton bean with prototype bean")
    void using_object_provider() {
        // IoC는 Dependency Injection과 Dependency Lookup 두 가지 종류가 있다.
        // ObjectProvider는 여기서 DL 기능을 제공하며, IoC 컨테이너에서 지정한 빈을 대신 찾아준다.
        // ObjectProvider는 ObjectFactory 인터페이스를 상속받아 편의 기능을 추가한 인터페이스다.

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ClientBeanWithObjectProvider.class, Prototype.class);

        ClientBeanWithObjectProvider clientBean1 = context.getBean(ClientBeanWithObjectProvider.class);
        int count1 = clientBean1.logic(); // Prototype bean is created
        assertThat(count1).isEqualTo(1);

        ClientBeanWithObjectProvider clientBean2 = context.getBean(ClientBeanWithObjectProvider.class); // Prototype bean is created
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

        context.close();
    }

    @Test
    @DisplayName("ObjectProvider를 사용해서 Singleton bean with prototype bean")
    void using_provider() {
        // IoC는 Dependency Injection과 Dependency Lookup 두 가지 종류가 있다.
        // ObjectProvider는 여기서 DL 기능을 제공하며, IoC 컨테이너에서 지정한 빈을 대신 찾아준다.
        // ObjectProvider는 ObjectFactory 인터페이스를 상속받아 편의 기능을 추가한 인터페이스다.

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ClientBeanWithProvider.class, Prototype.class);

        ClientBeanWithProvider clientBean1 = context.getBean(ClientBeanWithProvider.class);
        int count1 = clientBean1.logic(); // Prototype bean is created
        assertThat(count1).isEqualTo(1);

        ClientBeanWithProvider clientBean2 = context.getBean(ClientBeanWithProvider.class); // Prototype bean is created
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

        context.close();
    }

    // default가 singleton이기 때문에 생략해도 된다.
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    static class ClientBean {

        private final Prototype prototype;

        @Autowired
        ClientBean(Prototype prototype) {
            this.prototype = prototype;
        }

        public int logic() {
            prototype.addCount();
            return prototype.getCount();
        }
    }

    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    static class ClientBeanWithObjectProvider {

        /**
         * @see org.springframework.beans.factory.ObjectProvider#getObject()
         * @see org.springframework.beans.factory.ObjectFactory#getObject()
         * @see org.springframework.beans.factory.support.DefaultListableBeanFactory.DependencyObjectProvider#getObject()
         */
        private final ObjectProvider<Prototype> prototypeBeanProvider;

        ClientBeanWithObjectProvider(ObjectProvider<Prototype> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            Prototype prototype = prototypeBeanProvider.getObject();
            prototype.addCount();
            return prototype.getCount();
        }
    }

    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    static class ClientBeanWithProvider {

        /**
         * 스프링 프레임워크에서 제공하는 ObjectProvider가 아닌 JSR-330 표준 라이브러리인
         * javax.inject.Provider를 사용하면 스프링 프레임워크와의 결합도를 낮출 수 있다.
         * 기능도 단순하기 때문에 단위 테스트를 작성하거나 mock 코드를 만들기 훨씬 쉬워진다.
         *
         * @see javax.inject.Provider#get()
         * @see <a href="https://jcp.org/en/jsr/detail?id=330">JSR 330: Dependency Injection for Java</a>
         */
        private final Provider<Prototype> prototypeBeanProvider;

        ClientBeanWithProvider(Provider<Prototype> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            Prototype prototype = prototypeBeanProvider.get();
            prototype.addCount();
            return prototype.getCount();
        }
    }

    /**
     * @see Scope#scopeName()
     */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class Prototype {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("Prototype bean is created");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Prototype bean is destroyed");
        }
    }
}
