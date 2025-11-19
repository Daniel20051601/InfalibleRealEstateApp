package com.infaliblerealestate.dominio.usecase

import com.infaliblerealestate.dominio.repository.UsuarioRepository
import javax.inject.Inject

class GetUsuariosUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke() = repository.getUsuarios()
}