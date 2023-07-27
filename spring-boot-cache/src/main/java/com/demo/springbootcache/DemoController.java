package com.demo.springbootcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final DemoService service;

    @Autowired
    public DemoController(DemoService demoService) {
        this.service = demoService;
    }

    @GetMapping
    public String getData() {
        final var data = service.getData();
        log.debug("data >>> {}", data);
        return "test";
    }

    @GetMapping("/refresh")
    public String getDataWithCachePut() {
        return service.getDataWithCachePut();
    }

    @GetMapping("/evict")
    public void evictCache() {
        service.cacheEvict();
    }

}
