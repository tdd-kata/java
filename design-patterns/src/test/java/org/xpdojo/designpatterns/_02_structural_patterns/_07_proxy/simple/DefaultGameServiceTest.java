package org.xpdojo.designpatterns._02_structural_patterns._07_proxy.simple;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._02_structural_patterns._07_proxy.GameService;
import org.xpdojo.designpatterns._02_structural_patterns._07_proxy.GameServiceProxy;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultGameServiceTest {

    @Test
    void sut_proxy_service() {
        GameService gameService = new GameServiceProxy();
        String game = gameService.startGame();
        assertThat(game).isEqualTo("Game started");
    }
}
