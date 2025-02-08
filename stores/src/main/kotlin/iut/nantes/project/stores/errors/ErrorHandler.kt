package iut.nantes.project.stores.errors

import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.NumberFormatException
import java.util.NoSuchElementException

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConstraintViolation(e: DataIntegrityViolationException) =
        ResponseEntity.status(HttpStatus.CONFLICT).body("Failure: ${e.message}")

    @ExceptionHandler(NoSuchElementException::class)
    fun handleConstraintViolation(e: NoSuchElementException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failure: ${e.message}")

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleConstraintViolation(e: EntityNotFoundException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure: ${e.message}")

    @ExceptionHandler(NumberFormatException::class)
    fun handleConstraintViolation(e: NumberFormatException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure: ${e.message}")

}