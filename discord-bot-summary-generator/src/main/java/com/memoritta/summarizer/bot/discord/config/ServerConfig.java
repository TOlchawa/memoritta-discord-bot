package com.memoritta.summarizer.bot.discord.config;

import jdk.jfr.Name;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "server-config")
public class ServerConfig {
    String recorderUri;
    String resetUri;
    String summaryGeneratorUri;
    String configurationSaveUri;
    String loadAllConfigurationSaveUri;
    String loadConfigurationSaveUri;
}


