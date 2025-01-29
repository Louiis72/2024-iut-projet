package iut.nantes.project.products.services

import iut.nantes.project.products.entities.ProductDto
import iut.nantes.project.products.repositories.ProductRepository

class ProductServices(val productRepository: ProductRepository) {
    fun createProduct(product:ProductDto):ProductDto{
        val productEntity = product.toEntity()
        val savedFamily = productRepository.save(productEntity)
        return savedFamily.toDto()
    }
}