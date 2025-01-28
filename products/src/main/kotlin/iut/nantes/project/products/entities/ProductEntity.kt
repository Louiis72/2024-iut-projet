package iut.nantes.project.products.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
class ProductEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    private val id: UUID,
    private val name: String,
    private val description: String,
    private val price: PriceEntity,
    private val familyEntity: FamilyEntity
)