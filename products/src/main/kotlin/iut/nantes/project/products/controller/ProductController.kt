package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyDto
import iut.nantes.project.products.entities.ProductDto
import iut.nantes.project.products.services.FamilyServices
import iut.nantes.project.products.services.ProductServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(val productServices: ProductServices) {
    @PostMapping
    fun createProduct(@Valid @RequestBody product:ProductDto) : ResponseEntity<ProductDto>{
        val createdProduct = productServices.createProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)

    }

    @GetMapping
    fun findAllProductsd() : ResponseEntity<List<ProductDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(productServices.findAllProducts())

    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id: String) : ResponseEntity<Any> {
        return try {
            val uuid = UUID.fromString(id)  // Tentative de conversion en UUID
            val product = productServices.getProductById(uuid.toString())
            ResponseEntity.ok(product)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUID invalide")
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun produit trouvée avec cet Id")
        }
    }

    @PutMapping("/{id}")
    fun updateProductById(@Valid @PathVariable("id") id: String,@Valid @RequestBody product: ProductDto) : ResponseEntity<ProductDto> {
        val modifiedProduct = productServices.updateProduct(id,product)
        return ResponseEntity.status(HttpStatus.OK).body(modifiedProduct)
    }

    @DeleteMapping("/{id}")
    fun deleteFamilyById(@PathVariable("id") id: String) : ResponseEntity<Any> {
        return try {
            val uuid = UUID.fromString(id)
            productServices.deleteProduct(uuid.toString())
            ResponseEntity.noContent().build()
        } catch (e:java.lang.IllegalArgumentException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }

    }
}