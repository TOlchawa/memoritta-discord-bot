package com.memoritta.summarizer.bot.discord.client;

import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TextMessageRecorderClient {
    public void record(Discussion discussion) {
        log.info("discussion: {}", discussion);
    }
}
