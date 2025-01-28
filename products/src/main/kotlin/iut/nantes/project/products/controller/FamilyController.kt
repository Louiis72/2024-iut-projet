package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyEntity
import iut.nantes.project.products.services.FamilyServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FamilyController(val familyServices: FamilyServices) {

    @PostMapping("/api/v1/families")
    fun createFamily(@RequestBody family:FamilyDto) : ResponseEntity<FamilyDto> {
        familyServices.createFamily(family).let { return ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @GetMapping("/api/v1/families")
    fun getFamilies() : List<String> {
        TODO()
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