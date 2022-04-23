package org.xpdojo.designpatterns._02_structural_patterns._07_proxy.jdk;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._02_structural_patterns._07_proxy.DefaultGameService;
import org.xpdojo.designpatterns._02_structural_patterns._07_proxy.GameService;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class JdkProxyTest {

    @Test
    void sut_reflect_proxy() {
        String game = dynamicProxy();
        assertThat(game).isEqualTo("Game started");
    }

    private String dynamicProxy() {
        GameService gameServiceProxy = getGameServiceProxy(new DefaultGameService());
        return gameServiceProxy.startGame();
    }

    private GameService getGameServiceProxy(GameService target) {
        return (GameService) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{GameService.class}, (proxy, method, args) -> {

                    System.out.println("pre method.invoke");

                    Object result = method.invoke(target, args);

                    System.out.println("post method.invoke");

                    return result;
                });
    }
}
