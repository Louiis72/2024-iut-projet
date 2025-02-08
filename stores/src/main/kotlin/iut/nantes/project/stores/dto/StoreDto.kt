package iut.nantes.project.stores.dto

import iut.nantes.project.stores.entities.StoreEntity
import jakarta.validation.constraints.Size

data class StoreDto(
    val id: Long,
    @field:Size(min = 3, max = 30, message = "Le nom doit être compris entre 3 et 30 caractères")
    val name: String,
    val contact: ContactDto,
    val products: MutableList<ProductDto>
) {
    fun toEntity(): StoreEntity {
        return StoreEntity(this.id, this.name, this.contact.toEntity(), this.products)
    }
}