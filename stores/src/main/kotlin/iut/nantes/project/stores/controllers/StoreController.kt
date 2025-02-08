package iut.nantes.project.stores.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.dto.StoreDto
import iut.nantes.project.stores.services.StoreServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.NoSuchElementException

@RestController
@RequestMapping("api/v1/stores")
class StoreController(val storeServices: StoreServices) {

    @PostMapping
    fun createStore(@RequestBody @Valid storeDto: StoreDto): ResponseEntity<StoreDto> {
        val createdStore = storeServices.createStore(storeDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore)
    }

    @GetMapping
    fun findAllStores(): ResponseEntity<List<StoreDto>> {
        val stores = storeServices.findAllStores()
        return ResponseEntity.status(HttpStatus.OK).body(stores)
    }

    @GetMapping("/{id}")
    fun findStoreById(@PathVariable id: String): ResponseEntity<Any> {
        return try {
            var idStore = id.toLong()
            val store = storeServices.findStoreById(idStore)
            ResponseEntity.ok(store)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id invalide")
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun store trouvée avec cet id")
        }
    }

    @PutMapping("/{id}")
    fun updateStoreById(
        @Valid @PathVariable("id") id: String,
        @Valid @RequestBody store: StoreDto
    ): ResponseEntity<StoreDto> {
        val modifiedStore = storeServices.updateStoreById(id, store)
        return ResponseEntity.status(HttpStatus.OK).body(modifiedStore)
    }

    @DeleteMapping("/{id}")
    fun deleteStoreById(@Valid @PathVariable("id") id: String) {
        try {
            storeServices.deleteStoreById(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
    }
}