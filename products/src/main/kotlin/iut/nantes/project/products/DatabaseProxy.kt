package iut.nantes.project.products

import iut.nantes.project.products.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class DatabaseProxy (val ProductRepository: ProductRepository) {
}