package com.infaliblerealestate.presentation.validation

import com.infaliblerealestate.dominio.usecase.GetUsuariosUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UsuarioValidator @Inject constructor(
    val usuarios: GetUsuariosUseCase
) {
    suspend fun validateNombre(nombre: String, usuarioIdActual: Int? = null): ValidationResult {
        if (nombre.length < 5) {
            return ValidationResult(
                isValid = false,
                errorMessage = "El nombre debe tener al menos 5 caracteres"
            )
        } else if (nombre.length > 70) {
            return ValidationResult(
                isValid = false,
                errorMessage = "El nombre no puede tener más de 70 caracteres"
            )
        } else if (!validateNombreUnico(nombre, usuarioIdActual).isValid) {
            return ValidationResult(
                isValid = false,
                errorMessage = "El nombre de usuario ya existe"
            )
        } else {
            return ValidationResult(isValid = true, errorMessage = "")
        }
    }

    fun validatePassword(contrasena: String): ValidationResult {
        if (contrasena.length < 8) {
            return ValidationResult(
                isValid = false,
                errorMessage = "La contraseña debe tener al menos 8 caracteres"
            )
        } else if (!contrasena.contains(Regex("[A-Z]")) || !contrasena.contains(Regex("[a-z]")) || !contrasena.contains(
                Regex("[0-9]")
            ) || !contrasena.contains(Regex("[^A-Za-z0-9]"))
        ) {
            return ValidationResult(
                isValid = false,
                errorMessage = "La contraseña debe tener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial"
            )
        } else if (contrasena.length > 50) {
            return ValidationResult(
                isValid = false,
                errorMessage = "La contraseña no puede tener más de 50 caracteres"
            )
        } else {
            return ValidationResult(isValid = true, errorMessage = "")
        }
    }

    suspend fun validateNombreUnico(nombre: String, usuarioIdActual: Int? = null): ValidationResult {
        return try {
            val lista = usuarios().first()
            val existe = lista.any {
                it.userName.equals(nombre, ignoreCase = true) && it.usuarioId != usuarioIdActual
            }

            if (existe) {
                ValidationResult(
                    isValid = false,
                    errorMessage = "El nombre de usuario ya existe"
                )
            } else {
                ValidationResult(isValid = true, errorMessage = "")
            }
        } catch (e: Exception) {
            ValidationResult(
                isValid = false,
                errorMessage = "Error al validar el nombre: ${e.message}"
            )
        }
    }

    suspend fun validateCredenciales(userName: String, password: String): ValidationResult {
        return try {
            val lista = usuarios().first()
            val user = lista.firstOrNull {
                it.userName.equals(userName, ignoreCase = true) && it.password == password
            }
            if (user != null) {
                ValidationResult(isValid = true, usuarioId = user.usuarioId, errorMessage = "")
            } else {
                ValidationResult(isValid = false, usuarioId = null, errorMessage = "Usuario o contraseña incorrectos")
            }
        } catch (e: Exception) {
            ValidationResult(isValid = false, errorMessage = "Error al validar credenciales: ${e.message}")
        }
    }


}
