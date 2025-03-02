package io.api.carrent.api.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.api.carrent.api.docs.SwaggerConstants.*;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = SECURITY_BEARER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        tags = {
                @Tag(name = AUTH_TAG),
                @Tag(name = USER_TAG),
                @Tag(name = VEHICLE_TAG),
                @Tag(name = VEHICLE_TYPE_TAG),
        }
)
public class SwaggerConfig {
    private Info ApiInfo() {
        return new Info()
                .title("Carrent API")
                .description("API to startRent vehicles");
    }

    @Bean
    public OpenAPI ApiDocs() {
        return new OpenAPI()
                .info(ApiInfo());
    }
}
