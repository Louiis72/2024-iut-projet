package iut.nantes.project.products.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController {
    @PostMapping("/api/v1/products")
    fun createProduct(){
        TODO()
    }

}