package iut.nantes.project.products.entities

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.util.*


data class ProductDto(
    val id: UUID,
    @field:Size(min = 2, max = 20, message = "Le nom doit être compris entre 2 et 20 caractères")
    val name: String,
    @field:Size(min = 5, max = 100, message = "La description doit être comprise entre 5 et 100 caractères")
    val description: String?,
    @field:Valid
    val price: PriceDto,
    val family: FamilyDto
) {
    fun toEntity(): ProductEntity {
        return ProductEntity(
            this.id,
            this.name,
            this.description,
            this.price,
            this.family.toEntity()
        )
    }
}
