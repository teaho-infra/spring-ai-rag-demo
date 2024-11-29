package net.teaho.demo.ai.spring.rag.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI restfulOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("springboot3.x demo")
                .description("Spring Boot3 Restful API")
                .version("V1.0.0")
                .license(new License().name("teaho's blog").url("http://blog.teaho.net")))
            .externalDocs(new ExternalDocumentation()
                .description("blog")
                .url("https://blog.teaho.net"));
    }

}