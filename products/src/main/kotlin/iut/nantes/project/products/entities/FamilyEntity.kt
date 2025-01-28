package iut.nantes.project.products.entities

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.UUID


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FamilyEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    private val id: UUID?,
    private val name: String,
    private val description: String) {
}