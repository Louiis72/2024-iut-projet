package iut.nantes.project.products.entities

import jakarta.persistence.*
import java.util.*

@Table(name = "products")
@Entity
class ProductEntity(
    @Id
    @Column(name = "product_id")
    var id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String?,
    @Embedded
    var price: PriceDto,
    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    var family: FamilyEntity
) {
    fun toDto(): ProductDto {
        return ProductDto(this.id, this.name, this.description, this.price, this.family.toDto())
    }
}