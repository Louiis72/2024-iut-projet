package iut.nantes.project.products.errors

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.NoSuchElementException

@ControllerAdvice
class ErrorHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConstraintViolation(e: DataIntegrityViolationException) = ResponseEntity.status(HttpStatus.CONFLICT).body("Failure: ${e.message}")

    @ExceptionHandler(NoSuchElementException::class)
    fun handleConstraintViolation(e: NoSuchElementException) = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failure: ${e.message}")
}