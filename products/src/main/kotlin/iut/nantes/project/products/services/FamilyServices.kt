package iut.nantes.project.products.services

import iut.nantes.project.products.controller.FamilyDto
import iut.nantes.project.products.repositories.FamilyRepository
import java.lang.Exception
import kotlin.concurrent.thread

class FamilyServices(val familyRepository:FamilyRepository) {

    fun createFamily(family: FamilyDto): FamilyDto {
        val familyEntity = family.toEntity()
        val savedFamily = familyRepository.save(familyEntity)
        return savedFamily.toDto()
    }
}