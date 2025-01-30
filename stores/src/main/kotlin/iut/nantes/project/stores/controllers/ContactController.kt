package iut.nantes.project.stores.controllers

import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.services.ContactServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/contacts")
class ContactController(val contactServices: ContactServices) {
    @PostMapping
    fun createContact(@RequestBody @Valid contactDto: ContactDto):ResponseEntity<ContactDto>{
        val createdContact = contactServices.createContact(contactDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact)
    }
}