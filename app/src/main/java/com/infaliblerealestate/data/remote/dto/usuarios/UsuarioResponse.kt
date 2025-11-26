package com.infaliblerealestate.data.remote.dto.usuarios

data class UsuarioResponse(
    val id: String,
    val userName: String,
    val email: String,
    val phoneNumber: String,
    val nombre: String,
    val apellido: String,
    val estadoUsuario: String,
    val rol: String
)
