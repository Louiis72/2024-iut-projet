package iut.nantes.project.stores.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.services.ContactServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/v1/contacts")
class ContactController(val contactServices: ContactServices) {
    @PostMapping
    fun createContact(@RequestBody @Valid contactDto: ContactDto):ResponseEntity<ContactDto>{
        val createdContact = contactServices.createContact(contactDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact)
    }

    @GetMapping
    fun findContact(@RequestBody city: String?):ResponseEntity<List<ContactDto>>{
        val contacts = contactServices.findAllContacts(city)
        println(jacksonObjectMapper().writeValueAsString(contacts))
        return ResponseEntity.status(HttpStatus.OK).body(contacts)
    }

    @GetMapping("/{id}")
    fun findContactById(@PathVariable id:String):ResponseEntity<Any>{
        return try {
            var idContact = id.toLong()
            val contact = contactServices.findContactById(idContact)
            ResponseEntity.ok(contact)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id invalide")
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun contact trouvée avec cet Id")
        }

    }
}