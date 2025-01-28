package iut.nantes.project.products.entities

import jakarta.persistence.*
import java.util.*

@Table(name = "products")
@Entity
class ProductEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    private val id: UUID,
    private val name: String,
    private val description: String,
    @Embedded
    private val price: Price,
    @OneToOne(cascade = [(CascadeType.ALL)])
    private val family: FamilyEntity
)

@Embeddable
data class Price(
    val amount: Double,
    val currency: String
)