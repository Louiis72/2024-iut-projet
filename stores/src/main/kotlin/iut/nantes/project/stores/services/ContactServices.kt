package iut.nantes.project.stores.services

import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.repository.ContactRepository
import org.springframework.stereotype.Service

@Service
class ContactServices(val contactRepository: ContactRepository) {
    fun createContact(contactDto:ContactDto):ContactDto{
        contactRepository.save(contactDto.toEntity())
        return contactDto
    }
}