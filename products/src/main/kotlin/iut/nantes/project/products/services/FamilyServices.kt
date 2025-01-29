package iut.nantes.project.products.services

import iut.nantes.project.products.controller.FamilyDto
import iut.nantes.project.products.entities.FamilyEntity
import iut.nantes.project.products.repositories.FamilyRepository
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.concurrent.thread

class FamilyServices(val familyRepository:FamilyRepository) {

    fun createFamily(family: FamilyDto): FamilyDto {
        val familyEntity = family.toEntity()
        val savedFamily = familyRepository.save(familyEntity)
        return savedFamily.toDto()
    }

    fun getFamilies():List<FamilyDto>{
        return familyRepository.findAll().map { it.toDto() }.toList()
    }

    fun getFamilyById(id:String):FamilyDto{
        val uuid = UUID.fromString(id)
        val result = familyRepository.findById(uuid).orElseThrow { NoSuchElementException("Aucune famille trouvée avec cet Id") }
        return result.toDto()


    }
}