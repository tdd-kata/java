package org.xpdojo.designpatterns._03_behavioral_patterns._02_command;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleTest {

    @Test
    void sut_console() {
        // Invoker
        Console console = new Console();

        // Receiver 1
        Light light = new Light();
        console.press(new LightOnCommand(light)); // Command 1

        assertThat(light.isOn()).isTrue();

        // Receiver 2
        Game game = new Game();
        console.press(new GameStartCommand(game)); // Command 2

        assertThat(game.isStarted()).isTrue();

        console.undo();
        assertThat(game.isStarted()).isFalse();

        console.undo();
        assertThat(light.isOn()).isFalse();
    }

    @Test
    void sut_executor_service() throws InterruptedException {
        Light light = new Light();
        Game game = new Game();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(light::on);
        executorService.submit(game::start);
        Thread.sleep(500);

        assertThat(light.isOn()).isTrue();
        assertThat(game.isStarted()).isTrue();

        executorService.submit(game::stop);
        executorService.submit(light::off);
        Thread.sleep(500);

        assertThat(game.isStarted()).isFalse();
        assertThat(light.isOn()).isFalse();

        executorService.shutdown();
    }

}
