package com.sundaymvp.invoice_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI invoiceOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("InvoicePro API")
                        .description("Professional Invoice Management System REST API")
                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Sunday Chibueze")
                                .email("support@invoicepro.com")
                                .url("https://invoicepro.com"))

                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(new ExternalDocumentation()
                        .description("InvoicePro Documentation")
                        .url("https://invoicepro.com/docs"));
    }
}