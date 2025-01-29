package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyDto
import iut.nantes.project.products.entities.ProductDto
import iut.nantes.project.products.services.FamilyServices
import iut.nantes.project.products.services.ProductServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(val productServices: ProductServices) {
    @PostMapping("/api/v1/products")
    fun createProduct(@Valid @RequestBody product:ProductDto) : ResponseEntity<ProductDto>{
        val createdProduct = productServices.createProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)

    }

}