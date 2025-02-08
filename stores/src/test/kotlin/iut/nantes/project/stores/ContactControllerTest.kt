package iut.nantes.project.stores

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.stores.dto.AddressDto
import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.entities.ContactEntity
import org.springframework.http.MediaType.APPLICATION_JSON
import iut.nantes.project.stores.repository.ContactRepository
import iut.nantes.project.stores.services.ContactServices
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ContactControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var contactRepository: ContactRepository

    @Autowired
    lateinit var contactServices: ContactServices

    @BeforeEach
    fun setup() {
        contactRepository.deleteAll()
    }

    @Test
    @Transactional
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
    @Transactional
    fun `CreateContact incorrect`() {
        // Création d'un contact avec un numéro de téléphone invalide
        val contact = exampleContact("louis.villate@gmail.com", "NUMERO INCORRECT")
        mockMvc.post("/api/v1/contacts") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(contact)
        }.andExpect {
            status { isBadRequest() }  // Vérifie que le contact n'a pas été créé
        }
    }

    @Test
    @Transactional
    fun `FindAll sans filtres`() {
        val contact = exampleContact("louis.villate@gmail.com")
        mockMvc.post("/api/v1/contacts") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(contact)
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.get("/api/v1/contacts")
            .andExpect {
                status { isOk() }  // Vérifie que le contact est trouvé
                content { contentType("application/json") }
                jsonPath("$[0].email") { value("louis.villate@gmail.com") }  // Vérifier que le mail est correct
            }
    }

    @Test
    @Transactional
    fun `FindAll avec filtres`() {
        val contact = exampleContact("louis.villate@gmail.com")
        mockMvc.post("/api/v1/contacts") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(contact)
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.get("/api/v1/contacts?city=Nantes")
            .andExpect {
                status { isOk() }  // Vérifie que le contact est trouvé
                content { contentType("application/json") }
                jsonPath("$[0].address.city") { value("Nantes") }  // Vérifier que la ville est Nantes
            }
    }

    @Test
    @Transactional
    fun `FindContactById existant`() {
        val id: Long = 3
        val contact1 =
            ContactEntity(id, "louis.villatte@gmail.com", "0786351941", AddressDto("3 rue", "Nantes", "44000"))
        contactRepository.save(contact1)
        // Je choisis 8 car c'est le 5eme contact cree donc il a 8 comme Id. Peut être à revoir ?
        mockMvc.get("/api/v1/contacts/8")
            .andExpect {
                status { isOk() }  // Vérifie que le contact est trouvé
                content { contentType("application/json") }
                jsonPath("$.address.city") { value("Nantes") }  // Vérifier que la ville est Nantes
            }
    }

    @Test
    @Transactional
    fun `FindContactById inexistant`() {
        val contact = exampleContact("louis.villate@gmail.com")
        mockMvc.post("/api/v1/contacts") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(contact)
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.get("/api/v1/contacts/45")
            .andExpect {
                status { isNotFound() }  // Vérifie que le contact n'est trouvé
            }
    }

    @Test
    @Transactional
    fun `FindContactById id invalide`() {
        mockMvc.get("/api/v1/contacts/ID-INVALIDE")
            .andExpect {
                status { isBadRequest() }  // Vérifie que la réponse renvoyé est 400
            }
    }

    @Test
    @Transactional
    fun `UpdateContact correct`() {
        val contact = exampleContact("louis.villate@gmail.com")
        contactServices.createContact(contact)

        val newContact = exampleContact("louis.villatte@laposte.net")

        mockMvc.put("/api/v1/contacts/1") { // Peut être à changer, l'ID est généré selon les anciens tests ??
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(newContact) // Informations du nouveau contact en body
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @Transactional
    fun `UpdateContact incorrect`() {
        val contact = exampleContact("louis.villatte@laposte.net")
        contactServices.createContact(contact)

        val newContact = exampleContact("lo")

        mockMvc.put("/api/v1/contacts/2") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(newContact) // Informations du nouveau contact en body
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    @Transactional
    fun `deleteContact correct`() {
        val contact = exampleContact("louis.villatte@laposte.net")
        contactServices.createContact(contact)

        mockMvc.delete("/api/v1/contacts/3")
            .andExpect {
                status { isNoContent() }
            }
    }

    @Test
    @Transactional
    fun `deleteContact incorrect`() {
        mockMvc.delete("/api/v1/contacts/INVALID")
            .andExpect {
                status { isBadRequest() }
            }
    }

    fun exampleContact(mail: String, phone: String = "0786351941"): ContactDto {
        return ContactDto(0L, mail, phone, AddressDto("rue Dupont", "Nantes", "44000"))
    }
}