package iut.nantes.project.products.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import iut.nantes.project.products.entities.FamilyDto
import iut.nantes.project.products.entities.PriceDto
import iut.nantes.project.products.entities.ProductDto
import iut.nantes.project.products.repositories.FamilyRepository
import iut.nantes.project.products.repositories.ProductRepository
import iut.nantes.project.products.services.FamilyServices
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val uuidFamily = UUID.randomUUID()

    @Autowired
    lateinit var familyRepository: FamilyRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var familyServices: FamilyServices

    @BeforeEach
    fun setup() {
        productRepository.deleteAll()
        familyRepository.deleteAll()

    }

    @Test
    fun `CreateProduct correct`() {
        val requestContent = exampleProductLinkedToExistantFamily("Lit kingsize")
        // Test de création d'une nouvelle famille de produits
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = requestContent
        }.andExpect {
            status { isCreated() }
            content { contentType("application/json") }
            jsonPath("$.name") { value("Lit kingsize") }  // Vérifier que le nom est correct
        }
    }

    @Test
    fun `CreateProduct incorrect`() {
        val requestContent = exampleProductLinkedToExistantFamily("L")
        // Test de création d'une nouvelle famille de produits
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = requestContent
        }.andExpect {
            status { isBadRequest() }
            content { contentType("application/problem+json") }
        }
    }

    @Test
    fun `CreateProduct famille inexistante`() {
        val requestContent = exampleProductLinkedToNothing("Lit kingsize")
        // Test de création d'une nouvelle famille de produits
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = requestContent
        }.andExpect {
            status { isBadRequest() }
        }
    }


    fun exampleProductLinkedToExistantFamily(name: String, description: String = "Description par défaut"): String {
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)

        var productDto = ProductDto(UUID.randomUUID(), name, description, PriceDto(100, "EUR"), family)


        return jacksonObjectMapper().writeValueAsString(productDto).trimIndent()
    }

    fun exampleProductLinkedToNothing(name: String, description: String = "Description par défaut"): String {
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        // La famille n'est pas créée
        //familyServices.createFamily(family)

        var productDto = ProductDto(UUID.randomUUID(), name, description, PriceDto(100, "EUR"), family)

        return jacksonObjectMapper().writeValueAsString(productDto).trimIndent()
    }


}
