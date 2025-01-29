package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyEntity
import iut.nantes.project.products.services.FamilyServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class FamilyController(val familyServices: FamilyServices) {

    @PostMapping("/api/v1/families")
    fun createFamily(@Valid @RequestBody family:FamilyDto) : ResponseEntity<FamilyDto> {
        val createdFamily = familyServices.createFamily(family)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFamily)
    }

    @GetMapping("/api/v1/families")
    fun finAllFamilies() : List<FamilyDto> {
        return familyServices.getFamilies()
    }

    @GetMapping("/api/v1/families/{id}")
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

    @PutMapping("/api/v1/families/{id}")
    fun updateFamilyById(@Valid @PathVariable("id") id: String,@Valid @RequestBody family: FamilyDto) : ResponseEntity<FamilyDto> {
        val modifiedFamily = familyServices.updateFamily(id,family)
        return ResponseEntity.status(HttpStatus.OK).body(modifiedFamily)
    }

    @DeleteMapping("/api/v1/families/{id}")
    fun deleteFamilyById(@PathVariable("id") id: String) : List<String> {
        TODO()
    }

}