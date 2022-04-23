package org.xpdojo.designpatterns._03_behavioral_patterns._07_observer;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.chat.ChatServer;
import org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.chat.User;

import static org.assertj.core.api.Assertions.assertThat;

class ChatServerTest {

    @Test
    void sut_chat_server() {
        ChatServer chatServer = new ChatServer();
        User user1 = new User("markruler");
        User user2 = new User("whiteship");

        chatServer.register("오징어게임", user1);
        chatServer.register("오징어게임", user2);
        assertThat(chatServer.getSubscribers().get("오징어게임")).contains(user1, user2);
        chatServer.sendMessage(user1, "오징어게임", "111");
        // [markruler] markruler: 111
        // [whiteship] markruler: 111

        chatServer.register("디자인패턴", user1);
        chatServer.sendMessage(user2, "디자인패턴", "222");
        // [markruler] whiteship: 222

        chatServer.unregister("오징어게임", user2);
        assertThat(chatServer.getSubscribers().get("오징어게임")).contains(user1);
        chatServer.sendMessage(user2, "오징어게임", "333");
        // [markruler] whiteship: 333
    }

}
