package com.infaliblerealestate.dominio.repository

import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.model.Categorias
import com.infaliblerealestate.dominio.model.EstadoPropiedad
import com.infaliblerealestate.dominio.model.Propiedades
import kotlinx.coroutines.flow.Flow

interface PropiedadesRepository {
    suspend fun getPropiedades(): Flow<List<Propiedades>>
    suspend fun getPropiedad(id: Int): Resource<Propiedades>
    suspend fun putPropiedad(id: Int, propiedad: Propiedades): Resource<Unit>
    suspend fun postPropiedad(propiedad: Propiedades): Resource<Propiedades>
    suspend fun deletePropiedad(id: Int): Resource<Unit>
    suspend fun getCategorias(): Flow<List<Categorias>>
    suspend fun getEstadoPropiedades(): Flow<List<EstadoPropiedad>>
}

