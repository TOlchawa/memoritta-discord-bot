package com.memoritta.summarizer.summary.generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
public class CreateSummaryController {

    @GetMapping("/summary/channel/{channel}")
    public String summary(@PathVariable String channel) {
        log.info("channel: {}", channel);
        return "";
    }

}
