package iut.nantes.project.stores.entities

import iut.nantes.project.stores.dto.AdressDto
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
    val address: AdressDto
){
    constructor() : this(0,"","",AdressDto()) {

    }

    fun toDto():ContactDto{
        return ContactDto(this.id,this.email,this.phone,this.address)
    }
}