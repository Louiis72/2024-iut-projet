package iut.nantes.project.products.repositories


import iut.nantes.project.products.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<ProductEntity, UUID> {

}