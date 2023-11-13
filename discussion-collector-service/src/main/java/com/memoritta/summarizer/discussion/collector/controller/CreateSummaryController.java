package com.memoritta.summarizer.discussion.collector.controller;

import com.memoritta.summarizer.discussion.collector.db.DiscussionsRepository;
import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class CreateSummaryController {

    private final DiscussionsRepository discussionsRepository;

    @PostMapping("/record/channel/{channel}")
    public String record(@PathVariable String channel, @RequestBody Discussion discussion) {
        log.info("channel: {}", channel);
        log.info("discussion: {}", discussion);
        discussionsRepository.save(discussion);
        return "ok";
    }

}
