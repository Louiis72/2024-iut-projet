package iut.nantes.project.products.entities

import jakarta.persistence.*
import java.util.UUID

@Entity
class FamilyEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    private val id: UUID,
    private val name: String,
    private val description: String
)