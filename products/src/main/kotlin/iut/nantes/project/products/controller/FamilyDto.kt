package iut.nantes.project.products.controller

import iut.nantes.project.products.entities.FamilyEntity
import java.util.*

data class FamilyDto(
    val id: UUID? = null,
    val name: String,
    val description: String
){
    fun toEntity():FamilyEntity{
        return FamilyEntity(
            id?:UUID.randomUUID(),
            this.name,
            this.description)
    }
}