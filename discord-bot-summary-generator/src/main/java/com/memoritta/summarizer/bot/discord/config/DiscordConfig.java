package com.memoritta.summarizer.bot.discord.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "discord-config")
public class DiscordConfig {
    String discordKey;
    List<String> admins;
}
