package iut.nantes.project.stores.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.repository.ContactRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

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
        print(jacksonObjectMapper().writeValueAsString(contactRepository.findAll()[0]) )
        val contact = contactRepository.findById(id).orElseThrow { NoSuchElementException("Aucun contact trouvée avec cet Id") }
        return contact.toDto()
    }
}