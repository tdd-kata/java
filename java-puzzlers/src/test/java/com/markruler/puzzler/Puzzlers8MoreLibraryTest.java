package com.markruler.puzzler;

import com.markruler.puzzler.fixture.PingPong;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("추가적인 라이브러리 퍼즐")
class Puzzlers8MoreLibraryTest {

    @Nested
    @DisplayName("Puzzle 76: 핑퐁")
    class Describe_Ping_Pong {

        @Disabled
        @Test
        @DisplayName("Thread를 시작할 때는 run() 대신 start()를 호출하세요.")
        void not_to_invoke_a_threads_run_method_when_you_mean_to_invoke_its_start_method() {
            Thread t = new Thread() {
                public void run() {
                    String pong = PingPong.pong();
                    System.out.println(pong);
                }
            };
            // t.run();
            t.start();
            System.out.println("ping");
        }
    }

}
