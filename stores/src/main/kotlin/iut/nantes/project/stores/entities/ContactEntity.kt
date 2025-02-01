package iut.nantes.project.stores.entities

import iut.nantes.project.stores.dto.AddressDto
import iut.nantes.project.stores.dto.ContactDto
import jakarta.persistence.*

@Entity
@Table(name = "contacts")
class ContactEntity(
    @Id @GeneratedValue
    @Column(name = "contact_id")
    val id: Long? = null,
    val email: String,
    val phone: String,
    @Embedded
    val address: AddressDto
){
    constructor() : this(0,"","",AddressDto()) {

    }

    fun toDto():ContactDto{
        return ContactDto(this.id,this.email,this.phone,this.address)
    }
}