package iut.nantes.project.products.controller

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
        // Optionnel : nettoyage ou initialisation avant chaque test
    }

    @Test
    fun `test creation d'une famille`() {
        val requestContent = """
            {
                "name": "Lits",
                "description": "Différents types de lits"
            }
        """.trimIndent()

        // Test de création d'une nouvelle famille de produits
        mockMvc.post("/api/v1/families") {
            contentType = APPLICATION_JSON
            content = requestContent
        }.andExpect {
            status { isCreated() }  // Vérifier si la réponse a un code 201
            content { contentType("application/json") }
            jsonPath("$.name") { value("Lits") }  // Vérifier que le nom est correct
            jsonPath("$.description") { value("Différents types de lits") }  // Vérifier que la description est correcte
        }
    }
}
