package com.infaliblerealestate.dominio.usecase

import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.model.Usuarios
import com.infaliblerealestate.dominio.repository.UsuarioRepository
import javax.inject.Inject

class SaveUsuarioUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(id: Int, usuario: Usuarios): Resource<Usuarios?> {
        return if(id == 0){
            repository.postUsuario(usuario)
        }else{
            when(repository.putUsuario(id, usuario)){
                is Resource.Success -> Resource.Success(usuario)
                is Resource.Error -> Resource.Error("Error al actualizar el usuario")
                else -> Resource.Error("Error desconocido")
            }
        }
    }
}