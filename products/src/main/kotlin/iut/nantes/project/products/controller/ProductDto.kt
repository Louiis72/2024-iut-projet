package iut.nantes.project.products.controller

import java.util.*


data class ProductDto (
    val id: UUID,
    val name: String,
    val description: String,
    val price:PriceDto,
    val family:FamilyDto
)

data class FamilyDto(
    val id: UUID,
    val name: String,
    val description: String
)

data class PriceDto(
    val amount: String,
    val currency: String
)