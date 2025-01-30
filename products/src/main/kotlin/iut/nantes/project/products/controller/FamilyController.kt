package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyDto
import iut.nantes.project.products.services.FamilyServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/families")
class FamilyController(val familyServices: FamilyServices) {
    @PostMapping
    fun createFamily(@Valid @RequestBody family: FamilyDto) : ResponseEntity<FamilyDto> {
        val createdFamily = familyServices.createFamily(family)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFamily)
    }

    @GetMapping
    fun findAllFamilies() : ResponseEntity<List<FamilyDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(familyServices.getFamilies())
    }

    @GetMapping("/{id}")
    fun getFamilyById(@PathVariable id: String) : ResponseEntity<Any> {
        return try {
            val uuid = UUID.fromString(id)  // Tentative de conversion en UUID
            val family = familyServices.getFamilyById(uuid.toString())
            ResponseEntity.ok(family)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUID invalide")
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune famille trouvée avec cet Id")
        }
    }

    @PutMapping("/{id}")
    fun updateFamilyById(@Valid @PathVariable("id") id: String,@Valid @RequestBody family: FamilyDto) : ResponseEntity<FamilyDto> {
        val modifiedFamily = familyServices.updateFamily(id,family)
        return ResponseEntity.status(HttpStatus.OK).body(modifiedFamily)
    }

    @DeleteMapping("/{id}")
    fun deleteFamilyById(@PathVariable("id") id: String) : ResponseEntity<Any> {
        return try {
            familyServices.deleteFamily(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }

    }

}