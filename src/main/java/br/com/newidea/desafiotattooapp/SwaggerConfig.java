package br.com.newidea.desafiotattooapp;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("API REST - Desafio TattooApp")
                .description("Desafio para entendimento do fonte do Tattooapp.")
                .termsOfServiceUrl("https://github.com/mauricio-melo")
                .contact(new Contact("Mauricio Melo", "www.linkedin.com/in/mauriciormelo", "mauriciomelo.contato@outlook.com"))
                .license("Licença")
                .licenseUrl("https://github.com/mauricio-melo")
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
