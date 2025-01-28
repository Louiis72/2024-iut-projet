package iut.nantes.project.products.entities

import iut.nantes.project.products.controller.PriceDto
import iut.nantes.project.products.controller.ProductDto
import jakarta.persistence.*
import java.util.*

@Table(name = "products")
@Entity
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    var id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String,
    @Embedded
    var price: PriceDto,
    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "family_id", nullable = false)
    var family: FamilyEntity
){
    fun toDto():ProductDto{
        return ProductDto(this.id,this.name,this.description,this.price,this.family.toDto())
    }
}