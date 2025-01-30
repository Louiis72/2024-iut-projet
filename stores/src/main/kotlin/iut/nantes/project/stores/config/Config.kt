package iut.nantes.project.stores.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Config {
    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            // Lien avec le service Product
            .baseUrl("http://localhost:8081")
            .build()
    }
}