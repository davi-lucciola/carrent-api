package io.api.carrent.infra.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProprieties {
    private String key;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
}
