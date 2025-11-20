package com.observability.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI observabilityCenterOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Observability Center API")
                .description("Full-stack observability platform API for collecting and visualizing logs, metrics, and events in real-time")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Observability Center")
                    .email("support@observability.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

