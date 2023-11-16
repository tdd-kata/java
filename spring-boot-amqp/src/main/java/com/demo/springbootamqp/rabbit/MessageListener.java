package com.demo.springbootamqp.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "sample.queue")
    public void receiveMessage(
            final Message message
    ) throws IOException {
        log.info(message.toString());

        final var dto = objectMapper.readValue(message.getBody(), SampleBodyDto.class);
        log.info(dto.toString());
    }

}
