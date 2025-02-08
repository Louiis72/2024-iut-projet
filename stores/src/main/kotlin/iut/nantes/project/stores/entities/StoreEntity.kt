package iut.nantes.project.stores.entities

import iut.nantes.project.stores.dto.ProductDto
import iut.nantes.project.stores.dto.StoreDto
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table


@Entity
@Table(name = "stores")
class StoreEntity(
    @Id
    @GeneratedValue
    @Column(name = "id_store")
    val id: Long,
    var name: String,
    @OneToOne
    @JoinColumn(name = "id_contact")
    var contact: ContactEntity,
    @ElementCollection
    var products: MutableList<ProductDto> = mutableListOf()
) {
    constructor() : this(0L, "", ContactEntity())

    fun toDto(): StoreDto {
        return StoreDto(this.id, this.name, this.contact.toDto(), this.products)
    }
}
