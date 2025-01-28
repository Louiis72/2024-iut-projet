package iut.nantes.project.products.entities

import jakarta.persistence.Entity
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
class PriceEntity(
    private val amount: Int,
    private val description: String
)