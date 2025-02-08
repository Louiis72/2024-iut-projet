package iut.nantes.project.stores.entities

import iut.nantes.project.stores.dto.AddressDto
import iut.nantes.project.stores.dto.ContactDto
import jakarta.persistence.*

@Entity
@Table(name = "contacts")
class ContactEntity(
    @Id @GeneratedValue
    @Column(name = "id_contact")
    var id: Long? = null,
    var email: String,
    var phone: String,
    @Embedded
    var address: AddressDto
){
    constructor() : this(0,"","",AddressDto()) {

    }

    fun toDto():ContactDto{
        return ContactDto(this.id!!,this.email,this.phone,this.address)
    }
}