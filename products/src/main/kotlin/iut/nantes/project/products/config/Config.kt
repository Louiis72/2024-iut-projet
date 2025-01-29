package iut.nantes.project.products.config

import iut.nantes.project.products.repositories.FamilyRepository
import iut.nantes.project.products.repositories.ProductRepository
import iut.nantes.project.products.services.FamilyServices
import iut.nantes.project.products.services.ProductServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
    @Bean
    fun familyServices(familyRepository: FamilyRepository) = FamilyServices(familyRepository)

    @Bean
    fun productServices(productRepository: ProductRepository) = ProductServices(productRepository)

}