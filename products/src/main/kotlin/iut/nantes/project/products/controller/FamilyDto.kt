package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyEntity
import jakarta.validation.constraints.Size
import java.util.*


data class FamilyDto(
    val id: UUID? = null,
    @field:Size(min=3,max=30, message = "Le nom doit être compris entre 3 et 30 caractères")
    val name: String,
    @field:Size(min=5,max=100, message = "La description doit être comprise entre 5 et 100 caractères")
    val description: String
){
    fun toEntity():FamilyEntity{
        return FamilyEntity(
            id?:UUID.randomUUID(),
            this.name,
            this.description)
    }
}