package com.markruler.spec.reflection.dynamicproxy;

import com.markruler.spec.reflection.Book;
import com.markruler.spec.reflection.dynamicproxy.BookService;
import com.markruler.spec.reflection.dynamicproxy.DefaultBookService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dynamic Proxy")
class DynamicProxyTest {

    BookService bookService = (BookService) Proxy.newProxyInstance(
            BookService.class.getClassLoader(),
            new Class[]{BookService.class},
            invocationHandler());

    private InvocationHandler invocationHandler() {
        return (proxy, method, args) -> {
            BookService bookService = new DefaultBookService();
            if (method.getName().equals("rent")) {
                System.out.println("before");
                Object invoke = method.invoke(bookService, args);
                System.out.println("after");

                return "proxy:" + invoke;
            }
            return method.invoke(bookService, args);
        };
    }

    @Test
    @DisplayName("JDK Proxy를 사용한 interface 기반의 Dynamic Proxy")
    void sut_interface_based_dynamic_proxy_using_jdk() {
        final String jdkProxyRent = bookService.rent(new Book("JDKProxy"));

        assertThat(jdkProxyRent).isEqualTo("proxy:rent:JDKProxy");

        final String jdkProxyReturns = bookService.returns(new Book("JDKProxy"));

        assertThat(jdkProxyReturns).isEqualTo("returns:JDKProxy");
    }

    // https://github.com/cglib/cglib/blob/RELEASE_3_3_0/cglib/src/test/java/net/sf/cglib/proxy/TestEnhancer.java
    @Test
    @DisplayName("CGLib을 사용한 interface 기반의 Dynamic Proxy")
    void sut_interface_based_dynamic_proxy_using_cglib() {
        final Book cglib = new Book("CGLib");

        // interface 기반의 Dynamic Proxy도 가능하다.
        MethodInterceptor handler = (obj, method, args, proxy) -> {
            BookService defaultBookService = new DefaultBookService();
            return "proxy:" + method.invoke(defaultBookService, args);
        };
        BookService defaultBookService = (BookService) Enhancer.create(BookService.class, handler);
        String cglibRent = defaultBookService.rent(cglib);

        assertThat(cglibRent).isEqualTo("proxy:rent:CGLib");
    }

    @Test
    @DisplayName("CGLib을 사용한 subclass 기반의 Dynamic Proxy")
    void sut_class_based_dynamic_proxy_using_cglib() {
        final Book cglib = new Book("CGLib");

        // interface 기반의 Dynamic Proxy도 가능하다.
        // 하지만 Subclass 기반이기 때문에 상속이 불가능한 final class, private 생성자만 있는 클래스에는 적용할 수 없다.
        MethodInterceptor handler = (obj, method, args, proxy) -> {
            DefaultBookService defaultBookService = new DefaultBookService();
            return "proxy:" + method.invoke(defaultBookService, args);
        };
        DefaultBookService defaultBookService = (DefaultBookService) Enhancer.create(DefaultBookService.class, handler);
        String cglibRent = defaultBookService.rent(cglib);

        assertThat(cglibRent).isEqualTo("proxy:rent:CGLib");
    }

    @Test
    @DisplayName("Byte Buddy를 사용한 subclass 기반의 Dynamic Proxy")
    void sut_class_based_dynamic_proxy_using_byte_buddy() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Book byteBuddy = new Book("Byte Buddy");

        // interface 기반의 Dynamic Proxy도 가능하다.
        DynamicType.Unloaded<DefaultBookService> dynamicType = new ByteBuddy()
                .subclass(DefaultBookService.class)
                .method(ElementMatchers.named("rent"))
                .intercept(InvocationHandlerAdapter.of((proxy, method, args) -> {
                    DefaultBookService defaultBookService = new DefaultBookService();
                    return "proxy:" + method.invoke(defaultBookService, args);
                }))
                .make();

        Class<? extends DefaultBookService> proxyClass = dynamicType
                .load(DefaultBookService.class.getClassLoader())
                .getLoaded();

        DefaultBookService defaultBookService = proxyClass
                .getConstructor()
                .newInstance();

        String byteBuddyRent = defaultBookService.rent(byteBuddy);

        assertThat(byteBuddyRent).isEqualTo("proxy:rent:Byte Buddy");
    }

}
