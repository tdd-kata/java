package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SlowJobController {

    @GetMapping("/process/{seconds}")
    public String process(
            @PathVariable int seconds
    ) throws InterruptedException {

        log.debug("Start process for {} seconds", seconds);
        long start = System.currentTimeMillis();

        Thread.sleep(Math.min(seconds, 60) * 1_000L); // max 60 seconds

        long end = System.currentTimeMillis();

        log.debug("Process for {} seconds", (end - start) / 1_000.0);

        return "shutdown!";
    }

}
