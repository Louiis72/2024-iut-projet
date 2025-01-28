package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyEntity
import org.springframework.web.bind.annotation.*

@RestController
class FamilyController {

    @PostMapping("/api/v1/families")
    fun createFamily() : List<String> {
        TODO()
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