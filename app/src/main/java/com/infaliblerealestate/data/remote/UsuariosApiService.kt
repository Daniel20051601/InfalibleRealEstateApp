package com.infaliblerealestate.data.remote

import com.infaliblerealestate.data.remote.dto.UsuariosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UsuariosApiService {
    @GET("api/Usuarios")
    suspend fun getUsuarios(): Response<List<UsuariosDto>>

    @GET("api/Usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Response<UsuariosDto?>

    @PUT("api/Usuarios/{id}")
    suspend fun putUsuario(@Path("id") id: Int, @Body usuario: UsuariosDto): Response<Unit>

    @POST("api/Usuarios")
    suspend fun postUsuario(@Body usuario: UsuariosDto): Response<UsuariosDto?>
}