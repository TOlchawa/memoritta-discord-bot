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
public class RecorderController {

    private final DiscussionsRepository discussionsRepository;

    @GetMapping("/record")
    public String record() {
        return "ok";
    }

    @PostMapping("/record")
    public String record(@RequestBody Discussion discussion) {
        log.info("channelId: {} ; channelName: {}", discussion.getChannelId(), discussion.getChannelName());
        log.info("discussion: {}", discussion.getServer());
        discussionsRepository.save(discussion);
        return "ok";
    }

}
