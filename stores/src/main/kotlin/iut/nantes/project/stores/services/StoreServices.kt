package iut.nantes.project.stores.services

import iut.nantes.project.stores.dto.ContactDto
import iut.nantes.project.stores.dto.ProductDto
import iut.nantes.project.stores.dto.StoreDto
import iut.nantes.project.stores.repository.ContactRepository
import iut.nantes.project.stores.repository.StoreRepository
import org.springframework.stereotype.Service
import kotlin.NoSuchElementException

@Service
class StoreServices(val storeRepository: StoreRepository, val contactRepository: ContactRepository) {
    fun createStore(storeDto: StoreDto): StoreDto {
        if (storeRepository.findById(storeDto.id).isPresent) {
            return storeDto
        }

        val existantContact = contactRepository.findById(storeDto.contact.id).isPresent

        // Création d'un contact s'il n'existe pas
        val newContact: ContactDto?
        if (!existantContact) {
            val contact = storeDto.contact.toEntity()
            contact.id = null
            newContact = contactRepository.save(contact).toDto()
        } else {
            newContact = storeDto.contact
        }

        val storeEntity = storeDto.toEntity()

        storeEntity.products = mutableListOf()
        storeEntity.contact = newContact.toEntity()

        return storeRepository.save(storeEntity).toDto()
    }

    fun findAllStores(): List<StoreDto> {
        return storeRepository.findAll().sortedBy { it.name }.map { it.toDto() }
    }

    fun findStoreById(id: Long): StoreDto {
        val store =
            storeRepository.findById(id).orElseThrow { NoSuchElementException("Aucun store trouvée avec cet Id") }
        return store.toDto()
    }

    fun updateStoreById(id: String, store: StoreDto): StoreDto {
        val idLong = id.toLong()
        val newStore =
            storeRepository.findById(idLong).orElseThrow { NoSuchElementException("Aucun store trouvée avec cet Id") }
        newStore.name = store.name

        val newContact = contactRepository.findById(store.contact.id)
            .orElseThrow { NoSuchElementException("Le contact n'existe pas") }
        newStore.contact = newContact

        return storeRepository.save(newStore).toDto()
    }

    fun deleteStoreById(id: String) {
        val idLong = id.toLong()
        val store = storeRepository.findById(idLong).orElseThrow {
            java.util.NoSuchElementException("Aucun store trouvé avec cet Id")
        }
        storeRepository.delete(store)
    }

    fun addProductQuantity(idStore: String, idProduct: String, quantityProduct: Int): ProductDto {
        val store = storeRepository.findById(idStore.toLong()).orElseThrow {
            java.util.NoSuchElementException("Aucun store trouvé avec cet Id")
        }
        val product = store.products.find { idProduct == it.id }?.apply {
            quantity += quantityProduct
        }
        return product!!
    }

    fun removeProductQuantity(idStore: String, idProduct: String, quantityProduct: Int): ProductDto {
        val store = storeRepository.findById(idStore.toLong()).orElseThrow {
            java.util.NoSuchElementException("Aucun store trouvé avec cet Id")
        }
        val product = store.products.find { idProduct == it.id }?.apply {
            quantity -= quantityProduct
        }
        return product!!
    }

    fun removeProductsFromStore(idStore: String, productIds: List<String>) {
        val store = storeRepository.findById(idStore.toLong()).orElseThrow {
            NoSuchElementException("Aucun store trouvé avec cet Id")
        }

        store.products.removeIf { product -> product.id in productIds }
        storeRepository.save(store)
    }
}