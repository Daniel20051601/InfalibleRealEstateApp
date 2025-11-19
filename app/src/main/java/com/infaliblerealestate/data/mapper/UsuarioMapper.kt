package com.infaliblerealestate.data.mapper

import com.infaliblerealestate.data.remote.dto.UsuariosDto
import com.infaliblerealestate.dominio.model.Usuarios

fun Usuarios.toDto() : UsuariosDto = UsuariosDto(
    usuarioId =  usuarioId,
    userName = userName,
    password = password
)

fun UsuariosDto.toDomain(): Usuarios = Usuarios(
    usuarioId = usuarioId,
    userName = userName,
    password = password
)