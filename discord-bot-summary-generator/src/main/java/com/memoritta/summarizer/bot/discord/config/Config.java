package com.memoritta.summarizer.bot.discord.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("config")
@Getter
@Setter
public class Config {
    String discordKey;
}
