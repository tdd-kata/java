package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.social;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SocialNetworkTest {

    @Nested
    class TemplateMethodTest {
        final String userName = "markruler";
        final String password = "password";
        final String message = "Hello, world!";

        @Test
        void sut_facebook() {
            SocialNetwork network = new Facebook(userName, password);
            assertThat(network.post(message)).isTrue();
        }

        @Test
        void sut_twitter() {
            SocialNetwork network = new Twitter(userName, password);
            assertThat(network.post(message)).isTrue();
        }
    }

}
