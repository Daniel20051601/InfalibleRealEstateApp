package com.infaliblerealestate.data.remote

import com.infaliblerealestate.data.remote.dto.UsuariosDto
import javax.inject.Inject


class UsuarioRemoteDataSource @Inject constructor(
    private val apiService: UsuariosApiService
) {
    suspend fun getUsuarios(): Resource<List<UsuariosDto>> {
        return try {
            val response = apiService.getUsuarios()
            if(response.isSuccessful){
                response.body().let{Resource.Success(it)}
            }else {
                return Resource.Error("Error ${response.code()}: ${response.message()} ")
            }
        }catch (e: Exception){
            return Resource.Error("Error ${e.localizedMessage} ?: Ocurrió un error al obtener los usuarios ")
        }
    }

    suspend fun getUsuario(id: Int): Resource<UsuariosDto?> {
        return try {
            val response = apiService.getUsuario(id)
            if(response.isSuccessful){
                response.body().let{Resource.Success(it)}
            }else{
                return Resource.Error("Error ${response.code()}: ${response.message()}")
            }
        }catch (e: Exception){
            return Resource.Error("Error ${e.localizedMessage} ?: Ocurrio un error al obtener el usuario")
        }
    }

    suspend fun putUsuario(id: Int, usuario: UsuariosDto): Resource<Unit>{
        return try{
            val response = apiService.putUsuario(id, usuario)
            if(response.isSuccessful){
                Resource.Success(Unit)
            }else{
                Resource.Error("Error ${response.code()}: ${response.message()}")
            }
        }catch (e: Exception){
            return Resource.Error("Error ${e.localizedMessage} ?: Error al actualizar el usuario")
        }
    }

    suspend fun postUsuario(usuario: UsuariosDto): Resource<UsuariosDto?>{
        return try{
            val response = apiService.postUsuario(usuario)
            if(response.isSuccessful){
                response.body().let{Resource.Success(it)}
            }else{
                return Resource.Error("Error ${response.code()}: ${response.message()}")
            }
        }catch (e: Exception){
            return Resource.Error("Error ${e.localizedMessage}?: Error al crear el usuario")
        }
    }
}