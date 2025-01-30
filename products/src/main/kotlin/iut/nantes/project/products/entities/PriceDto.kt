package iut.nantes.project.products.entities

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Pattern


data class PriceDto(
    @field:DecimalMin(value = "0.01", inclusive = true, message = "Le montant doit être un nombre positif")
    val amount: Int = 0,
    @field:Pattern(regexp = "^[A-Z]{3}$", message = "La devise doit être composée de 3 caractères alphabétiques en majuscule (ex: EUR)")
    val currency: String = ""
)