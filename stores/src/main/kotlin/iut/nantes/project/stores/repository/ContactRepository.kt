package iut.nantes.project.stores.repository

import iut.nantes.project.stores.entities.ContactEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContactRepository : JpaRepository<ContactEntity, Long>