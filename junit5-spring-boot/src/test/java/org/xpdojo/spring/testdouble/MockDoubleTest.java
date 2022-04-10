package org.xpdojo.spring.testdouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.MockHandler;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.mock.MockCreationSettings;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MockDoubleTest {

    @Test
    @DisplayName("Mockito를 사용해서 Mocking test")
    void test_mockito() {
        MockitoDouble mockitoDouble = mock(MockitoDouble.class);

        given(mockitoDouble.greeting(eq("markruler"))).willReturn("Hello markruler");

        final String actual = mockitoDouble.greeting("markruler");
        assertThat(actual).isEqualTo("Hello markruler");

        verify(mockitoDouble).greeting(anyString());
    }

    /**
     * Mockito는 ByteBuddy를 사용하지만 여기서는 최대한 단순화시켰다.
     *
     * @see org.mockito.internal.creation.proxy.ProxyMockMaker#createMock(MockCreationSettings, MockHandler)
     */
    @Test
    @DisplayName("동적 프록시를 사용해서 Mocking test")
    void test_mock_object() {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("name")
                        && method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0] == String.class
                        && !ObjectUtils.isEmpty(args[0])) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Hello ");
                    sb.append(args[0]);
                    return sb.toString();
                } else {
                    return null;
                }
            }
        };

        MockDouble mock =
                (MockDouble) Proxy.newProxyInstance(
                        getClass().getClassLoader(),
                        new Class[]{MockDouble.class},
                        invocationHandler
                );

        assertThat(mock.name("makruler")).isEqualTo("Hello makruler");

        assertThat(mock.name(null)).isBlank();
        assertThat(mock.name("")).isBlank();
    }
}
