package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyEntity
import iut.nantes.project.products.services.FamilyServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun getFamilyById() : List<String> {
        TODO()
    }

    @PutMapping("/api/v1/families/{id}")
    fun updateFamilyById(@PathVariable("id") id: String, @RequestBody family: FamilyEntity) : List<String> {
        TODO()
    }

    @DeleteMapping("/api/v1/families/{id}")
    fun deleteFamilyById(@PathVariable("id") id: String) : List<String> {
        TODO()
    }

}