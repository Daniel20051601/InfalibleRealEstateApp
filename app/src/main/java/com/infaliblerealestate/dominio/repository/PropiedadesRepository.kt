package com.infaliblerealestate.dominio.repository

import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.model.Propiedades
import kotlinx.coroutines.flow.Flow

interface PropiedadesRepository {
    suspend fun getPropiedades(): Flow<List<Propiedades>>
    suspend fun getPropiedad(id: Int): Resource<Propiedades>
    suspend fun putPropiedad(id: Int, propiedad: Propiedades): Resource<Unit>
}