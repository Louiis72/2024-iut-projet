package iut.nantes.project.products.entities

import iut.nantes.project.products.controller.FamilyDto
import jakarta.persistence.*
import java.util.UUID

@Table(name = "families")
@Entity
class FamilyEntity(
    @Id
    @Column(name = "family_id")
    var id: UUID = UUID.randomUUID(),
    @Column(name="name",unique=true)
    var name: String,
    var description: String,
    @OneToMany(mappedBy = "family", cascade = [CascadeType.ALL])
    var products : List<ProductEntity>? = null
){
    fun toDto():FamilyDto{
        return FamilyDto(this.id,this.name,this.description)
    }
}