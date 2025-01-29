package iut.nantes.project.products.controller

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

    val uuidFamily : UUID = UUID.randomUUID()

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
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)

        val requestContent = exampleProductLinkedFamily("Lit kingsize", null, family)
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
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val requestContent = exampleProductLinkedFamily("L", null, family)
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
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        //familyServices.createFamily(family)
        val requestContent = exampleProductLinkedFamily("Lit kingsize", null, family)
        // Test de création d'une nouvelle famille de produits
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = requestContent
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `FindAllProduct rempli`() {
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)

        val requestContent1 = exampleProductLinkedFamily("Lit kingsize", null, family)
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = requestContent1
        }.andExpect {
            status { isCreated() }
        }

        val requestContent2 = exampleProductLinkedFamily("Lit double", null, family)
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = requestContent2
        }.andExpect {
            status { isCreated() }
        }

        // Récupérer tous les produits et vérifier les noms
        mockMvc.get("/api/v1/products")
            .andExpect {
                status { isOk() }
                jsonPath("$[0].name") { value("Lit kingsize") }
                jsonPath("$[1].name") { value("Lit double") }
            }
    }

    @Test
    fun `FindProductById existant`() {
        val uuidProduct = UUID.randomUUID()
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)
        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }
        mockMvc.get("/api/v1/products/$uuidProduct")
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("Lit kingsize") }
            }
    }

    @Test
    fun `FindProductById inexistant`() {
        val uuid = UUID.randomUUID().toString()
        mockMvc.get("/api/v1/products/$uuid")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `FindProductById id invalide`() {
        val uuid = "invalid-uuid"
        mockMvc.get("/api/v1/products/$uuid")
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `UpdateProduct correct`() {
        val uuidProduct = UUID.randomUUID()
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)

        val newProduct =
            ProductDto(uuidProduct, "Lit kingsize modifié", "Description du lit", PriceDto(100, "EUR"), family)


        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.put("/api/v1/products/$uuidProduct") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(newProduct).trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value("Lit kingsize modifié") }
        }
    }

    @Test
    fun `UpdateProduct incorrect`() {
        val uuidProduct = UUID.randomUUID()
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)
        val newProduct = ProductDto(uuidProduct, "L", "Description du lit", PriceDto(10, "EUR"), family)


        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.put("/api/v1/products/$uuidProduct") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(newProduct).trimIndent()
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `UpdateProduct famille inexistante`() {
        val uuidProduct = UUID.randomUUID()
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        val family2 = FamilyDto(UUID.randomUUID(), "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)
        val newProduct = ProductDto(uuidProduct, "L", "Description du lit", PriceDto(10, "EUR"), family2)

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.put("/api/v1/products/$uuidProduct") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(newProduct).trimIndent()
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `DeleteProduct correct`() {
        val uuidProduct = UUID.randomUUID()
        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }
        mockMvc.delete("/api/v1/products/$uuidProduct")
            .andExpect {
                status { isNoContent() }
            }
    }

    @Test
    fun `DeleteProduct id inexistant`() {
        val uuidProduct = UUID.randomUUID()
        val uuidProduct2 = UUID.randomUUID()

        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }
        mockMvc.delete("/api/v1/products/$uuidProduct2")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `DeleteProduct id invalide`() {
        val uuidProduct = UUID.randomUUID()
        val invalidUuid = "invalid-uuid-format"

        val family = FamilyDto(uuidFamily, "Famille", "Description")
        familyServices.createFamily(family)
        val product = ProductDto(uuidProduct, "Lit kingsize", "Description du lit", PriceDto(100, "EUR"), family)

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(product).trimIndent()
        }.andExpect {
            status { isCreated() }
        }
        mockMvc.delete("/api/v1/products/$invalidUuid")
            .andExpect {
                status { isBadRequest() }
            }
    }

    fun exampleProductLinkedFamily(
        name: String,
        description: String? = "Description par défaut",
        family: FamilyDto
    ): String {
        val productDto = ProductDto(UUID.randomUUID(), name, description, PriceDto(100, "EUR"), family)
        return jacksonObjectMapper().writeValueAsString(productDto).trimIndent()
    }
}
