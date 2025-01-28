package iut.nantes.project.products.controller

import com.fasterxml.jackson.databind.BeanDescription
import iut.nantes.project.products.repositories.FamilyRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class FamilyControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var familyRepository: FamilyRepository

    @BeforeEach
    fun setup() {
        familyRepository.deleteAll()
    }

    @Test
    fun `test creation d'une famille`() {
        val requestContent = exampleFamily("Lits")
        // Test de création d'une nouvelle famille de produits
        mockMvc.post("/api/v1/families") {
            contentType = APPLICATION_JSON
            content = requestContent
        }.andExpect {
            status { isCreated() }  // Vérifier si la réponse a un code 201
            content { contentType("application/json") }
            jsonPath("$.name") { value("Lits") }  // Vérifier que le nom est correct
            jsonPath("$.description") { value("Description intriguante") }  // Vérifier que la description est correcte
        }
    }

    @Test
    fun `test finAllFamilies`() {
        val famille1 = exampleFamily("Lits")
        val famille2 = exampleFamily("Tables")

        mockMvc.post("/api/v1/families") {
            contentType = APPLICATION_JSON
            content = famille1
        }
        mockMvc.post("/api/v1/families") {
            contentType = APPLICATION_JSON
            content = famille2
        }
        // Récupération de toutes les familles
        mockMvc.get("/api/v1/families") {
        }.andExpect {
            status { isOk() }  // Vérifie que la réponse est OK
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Lits") }  // Vérifie que la première famille a bien le nom "Lits"
            jsonPath("$[1].name") { value("Tables") }  // Vérifie que la deuxième famille a bien le nom "Tables"
        }


    }

    fun exampleFamily(name:String):String{
        return """
            {
                "name": "$name",
                "description": "Description intriguante"
            }
        """.trimIndent()
    }
}
