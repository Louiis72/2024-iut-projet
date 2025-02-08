package iut.nantes.project.products.repositories

import iut.nantes.project.products.entities.FamilyEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FamilyRepository : JpaRepository<FamilyEntity, UUID>