package iut.nantes.project.products.services

import iut.nantes.project.products.entities.ProductDto
import iut.nantes.project.products.repositories.ProductRepository
import java.util.*

class ProductServices(val productRepository: ProductRepository) {
    fun createProduct(product: ProductDto): ProductDto {
        val productEntity = product.toEntity()
        val savedFamily = productRepository.save(productEntity)
        return savedFamily.toDto()
    }

    fun findAllProducts(): List<ProductDto> {
        return productRepository.findAll().map { it.toDto() }.toList()
    }

    fun getProductById(id: String): ProductDto {
        val uuid = UUID.fromString(id)
        val result =
            productRepository.findById(uuid).orElseThrow { NoSuchElementException("Aucun produit trouvé avec cet Id") }
        return result.toDto()
    }

    fun updateProduct(id: String, product: ProductDto): ProductDto {
        var newProduct = productRepository.findById(UUID.fromString(id))
            .orElseThrow { NoSuchElementException("Aucun produit trouvé avec cet Id") }
        newProduct.name = product.name
        newProduct.description = product.description
        newProduct.price = product.price
        newProduct.family = product.family.toEntity()

        productRepository.save(newProduct)
        return newProduct.toDto()
    }

    fun deleteProduct(id: String) {
        val uuid = UUID.fromString(id)
        val product = productRepository.findById(uuid).orElseThrow {
            NoSuchElementException("Aucun produit trouvé avec cet Id")
        }
        productRepository.delete(product)
    }
}