package iut.nantes.project.stores

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.stores.dto.AdressDto
import iut.nantes.project.stores.dto.ContactDto
import org.springframework.http.MediaType.APPLICATION_JSON
import iut.nantes.project.stores.repository.ContactRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var contactRepository: ContactRepository

    @BeforeEach
    fun setup() {
        contactRepository.deleteAll()
    }

    @Test
    fun `CreateContact correct`() {
        // Création d'un contact valide
        val contact = exampleContact("louis.villate@gmail.com")
        mockMvc.post("/api/v1/contacts") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(contact)
        }.andExpect {
            status { isCreated() }  // Vérifie que le contact a bien été créé
            content { contentType("application/json") }
            jsonPath("$.email") { value("louis.villate@gmail.com") }
            jsonPath("$.phone") { value("0786351941") }
        }
    }

    @Test
    fun `CreateContact incorrect`() {
        // Création d'un contact avec un numéro de téléphone invalide
        val contact = exampleContact("louis.villate@gmail.com","NUMERO INCORRECT")
        mockMvc.post("/api/v1/contacts") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(contact)
        }.andExpect {
            status { isBadRequest() }  // Vérifie que le contact n'a pas été créé
        }
    }

    fun exampleContact(mail:String,phone:String = "0786351941"): ContactDto {
        return ContactDto(0L, mail,phone, AdressDto("rue Dupont","Nantes","44000"))
    }
}