package iut.nantes.project.stores.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.repository.ContactRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContactServices(val contactRepository: ContactRepository) {
    fun createContact(contactDto:ContactDto):ContactDto{
        contactRepository.save(contactDto.toEntity())
        return contactDto
    }

    fun findAllContacts(city:String?):List<ContactDto>{
        if (city == null){
            return contactRepository.findAll().map { it.toDto() }.toList()
        }
        return contactRepository.findAll().filter { it.address.city == city }.map { it.toDto() }.toList()
    }

    fun findContactById(id:Long):ContactDto{
        //print(jacksonObjectMapper().writeValueAsString(contactRepository.findAll()[0]) )
        val contact = contactRepository.findById(id).orElseThrow { NoSuchElementException("Aucun contact trouvée avec cet Id") }
        return contact.toDto()
    }

    fun updateContactById(id:String,contact:ContactDto):ContactDto{
        //print(jacksonObjectMapper().writeValueAsString(contactRepository.findAll()[0]) )

        val idLong = id.toLong()
        val newContact = contactRepository.findById(idLong).orElseThrow { NoSuchElementException("Aucun contact trouvée avec cet Id") }
        newContact.email = contact.email
        newContact.address = contact.address
        newContact.phone = contact.phone
        return contactRepository.save(newContact).toDto()
    }

    fun deleteContactById(id:String){
        //print(jacksonObjectMapper().writeValueAsString(contactRepository.findAll()[0]) )

        val idLong = id.toLong()
        val contact = contactRepository.findById(idLong).orElseThrow {
            NoSuchElementException("Aucun contact trouvé avec cet Id")
        }
        contactRepository.delete(contact)
    }
}