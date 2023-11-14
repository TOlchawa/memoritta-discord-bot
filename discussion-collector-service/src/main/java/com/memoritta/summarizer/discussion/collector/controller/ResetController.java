package com.memoritta.summarizer.discussion.collector.controller;

import com.memoritta.summarizer.discussion.collector.db.DiscussionsProvider;
import com.memoritta.summarizer.discussion.collector.db.DiscussionsRepository;
import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class ResetController {

    private final DiscussionsProvider discussionsProvider;

    @GetMapping("/reset/server/{server}/channel/{channel}")
    public String record(@PathVariable String server, @PathVariable String channel) {
        log.info("server: {} ;channel: {}", server, channel);
        discussionsProvider.reset(server, channel);
        return "ok";
    }

}
