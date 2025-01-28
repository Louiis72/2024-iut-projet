package iut.nantes.project.products.config

import iut.nantes.project.products.repositories.FamilyRepository
import iut.nantes.project.products.services.FamilyServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
    @Bean
    fun familyServices(familyRepository: FamilyRepository) = FamilyServices(familyRepository)
}