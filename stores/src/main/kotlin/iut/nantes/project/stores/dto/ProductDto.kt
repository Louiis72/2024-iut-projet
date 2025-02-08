package iut.nantes.project.stores.dto

import jakarta.persistence.Embeddable

@Embeddable
data class ProductDto(
    val id: String,
    var name: String,
    var quantity: Int
) {
    constructor() : this("", "", 0)
}