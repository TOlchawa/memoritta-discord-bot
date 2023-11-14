package com.memoritta.summarizer.summary.generator.client;

import com.memoritta.summarizer.domain.Discussion;
import com.memoritta.summarizer.summary.generator.db.DiscussionsProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryGenerator {
    private final DiscussionsProvider discussionsProvider;
    private final ChatClient chatClient;


    public String generateSummaryForChannel(String server, String channelId) {
        List<Discussion> context = discussionsProvider.getDiscussionsForChannel(server, channelId);
        String response = chatClient.askQuestion(context, ChatClient.DEFAULT_QUESTION);
        log.info("result: {}", response);
        return response;
    }

}
