package me.lucasamscc.taskmanager.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager - API do desafio")
                        .description("Documentação da API - Task Manager")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lucas de Andrade Martins")
                                .url("https://github.com/lucasamscc/")));
    }
}
