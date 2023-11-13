package com.memoritta.summarizer.summary.generator.client;

import com.memoritta.summarizer.domain.Discussion;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChatClient {
    public static final String USE_BELOW_MESSAGES_AS_CONTEXT = "Ponizej znajduje sie konwersacja pomiedzy uzytkownikami channel-a";
    public static final String DEFAULT_QUESTION = "Jako eksper w dziedzinach poruszanych w konwersacji wygeneruj krotkie podsumowanie z dyskusji";
    ChatLanguageModel chatLanguageModel;

    public String askQuestion(List<Discussion> contextVectors, String question) {

        List<ChatMessage> conversation = prepareMessages(contextVectors, question);
        Instant startT = Instant.now();
        AiMessage answer = chatLanguageModel.sendMessages(conversation);
        log.info("Response generated in {}", Duration.between(startT, Instant.now()).toSeconds());
        return answer.text();
    }

    private List<ChatMessage> prepareMessages(List<Discussion> contextVectors, String question) {
        List<ChatMessage> result = new ArrayList<>();
        result.add(new SystemMessage(USE_BELOW_MESSAGES_AS_CONTEXT));
        contextVectors.forEach(v -> result.add(new SystemMessage(v.getText())));
        result.add(new SystemMessage(DEFAULT_QUESTION));
        result.add(new UserMessage(question));
        return result;
    }
}
