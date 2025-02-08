package iut.nantes.project.stores.dto

import iut.nantes.project.stores.entities.ContactEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

data class ContactDto (
    val id: Long,
    @field:Email
    val email: String,
    @field:Pattern(regexp = "\\d{10}", message = "Le téléphone doit avoir 10 chiffres.")
    val phone: String,
    val address: AddressDto
){
    fun toEntity():ContactEntity{
        return ContactEntity(this.id,this.email,this.phone,this.address)
    }
}