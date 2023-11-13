package com.memoritta.summarizer.discussion.collector.controller;

import com.memoritta.summarizer.domain.Discussion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
public class CreateSummaryController {

    @GetMapping("/record/channel/{channel}")
    public String record(@PathVariable String channel, @RequestBody Discussion discussion) {
        log.info("channel: {}", channel);
        log.info("discussion: {}", discussion);
        return "";
    }

}
