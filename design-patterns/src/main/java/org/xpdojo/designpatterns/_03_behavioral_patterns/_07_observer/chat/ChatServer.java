package org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="http://www.oodesign.com/observer-pattern.html">Observer Pattern</a>
 * @see java.lang.ref.WeakReference
 * @see java.util.Observable
 * @see java.util.Observer
 * @see java.beans.PropertyChangeListener
 * @see java.util.concurrent.Flow
 */
public class ChatServer {

    private Map<String, List<Subscriber>> subscribers = new HashMap<>();

    public Map<String, List<Subscriber>> getSubscribers() {
        return subscribers;
    }

    public void register(String subject, Subscriber subscriber) {
        if (this.subscribers.containsKey(subject)) {
            this.subscribers.get(subject).add(subscriber);
        } else {
            List<Subscriber> list = new ArrayList<>();
            list.add(subscriber);
            this.subscribers.put(subject, list);
        }
    }

    public void unregister(String subject, Subscriber subscriber) {
        if (this.subscribers.containsKey(subject)) {
            this.subscribers.get(subject).remove(subscriber);
        }
    }

    public void sendMessage(User user, String subject, String message) {
        if (this.subscribers.containsKey(subject)) {
            String userMessage = user.getName() + ": " + message;
            this.subscribers.get(subject).forEach(s -> s.handleMessage(userMessage));
        }
    }

}