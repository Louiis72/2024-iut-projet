package iut.nantes.project.products.entities

import jakarta.persistence.*
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
    private val id: UUID? = null,
    private val name: String = "",
    private val description: String = "",
    @OneToOne(cascade = [(CascadeType.ALL)])
    private val price: PriceEntity? = null,
    @OneToOne(cascade = [(CascadeType.ALL)])
    private val familyEntity: FamilyEntity? = null
)