package com.memoritta.summarizer.summary.generator.controller;

import com.memoritta.summarizer.summary.generator.client.SummaryGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@AllArgsConstructor
public class CreateSummaryController {

    private final SummaryGenerator summaryGenerator;

    @GetMapping("/summary/server/{server}/channel/{channel}")
    public String summary(@PathVariable String server, @PathVariable String channel) {
        log.info("server: {}; channel: {}", server, channel);
        return summaryGenerator.generateSummaryForChannel(server, channel);
    }

}
