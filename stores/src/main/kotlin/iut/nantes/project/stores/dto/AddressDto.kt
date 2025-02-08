package iut.nantes.project.stores.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AddressDto(
    @field:Size(min = 5, max = 50, message = "La rue doit faire entre 5 et 50 caractères.")
    val street: String,

    @field:Size(min = 1, max = 30, message = "La ville doit faire entre 1 et 30 caractères.")
    val city: String,

    @field:Pattern(regexp = "\\d{5}", message = "Le code postal doit être un code postal valide (5 chiffres).")
    val postalCode: String
) {
    constructor() : this("", "", "")
}

