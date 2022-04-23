package org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyEventListener {

    @EventListener(MyEvent.class)
    public void onApplicationEvent(MyEvent event) {
        log.info(event.getMessage());
    }
}
