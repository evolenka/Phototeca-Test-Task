package by.evlashkina.crypto.telegram;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("bot")

public class BotProperties {
    private String username;
    private String token;
}
