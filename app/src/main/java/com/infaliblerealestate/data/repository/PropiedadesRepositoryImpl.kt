package com.infaliblerealestate.data.repository

import com.infaliblerealestate.data.mapper.toDomain
import com.infaliblerealestate.data.mapper.toRequest
import com.infaliblerealestate.data.remote.propiedades.PropiedadesRemoteDataSource
import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.model.Categorias
import com.infaliblerealestate.dominio.model.EstadoPropiedad
import com.infaliblerealestate.dominio.model.Propiedades
import com.infaliblerealestate.dominio.repository.PropiedadesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PropiedadesRepositoryImpl @Inject constructor(
    private val remoteDataSource: PropiedadesRemoteDataSource
): PropiedadesRepository {

    override suspend fun getPropiedades(): Flow<List<Propiedades>> = flow {
        when(val result = remoteDataSource.getPropiedades()){
            is Resource.Success -> {
                val list = result.data?.map{it.toDomain()} ?: emptyList()
                emit(list)
            }
            is Resource.Error -> { emit(emptyList())}
            else -> emit(emptyList())
        }
    }

    override suspend fun getPropiedad(id: Int): Resource<Propiedades> {
        return when (val result = remoteDataSource.getPropiedad(id)) {
            is Resource.Success -> {
                val propiedad = result.data
                Resource.Success(propiedad?.toDomain())
            }

            is Resource.Error -> {
                Resource.Error(result.message ?: "Error")
            }

            else -> {
                Resource.Error("Error al obtener la propiedad")
            }
        }
    }

    override suspend fun putPropiedad(id: Int, propiedad: Propiedades): Resource<Unit> {
        return when(val result = remoteDataSource.putPropiedad(id, propiedad.toRequest())){
            is Resource.Success -> {
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error(result.message ?: "Error")
            }
            else -> {
                Resource.Error("Error al actualizar la propiedad")
            }
        }
    }

    override suspend fun postPropiedad(propiedad: Propiedades): Resource<Propiedades> {
        return when(val result = remoteDataSource.postPropiedad(propiedad.toRequest())){
            is Resource.Success -> {
                val propiedad = result.data?.toDomain()
                Resource.Success(propiedad)
            }
            is Resource.Error -> {
                Resource.Error(result.message ?: "Error")
            }
            else -> {
                Resource.Error("Error al crear la propiedad")
            }

        }
    }

    override suspend fun deletePropiedad(id: Int): Resource<Unit> {
        return when(val result = remoteDataSource.deletePropiedad(id)){
            is Resource.Success -> {
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error(result.message ?: "Error")
            }
            else -> {
                Resource.Error("Error al eliminar la propiedad")
            }
        }
    }

    override suspend fun getCategorias(): Flow<List<Categorias>> = flow{
        when(val result = remoteDataSource.getCategorias()){
            is Resource.Success -> {
                val list = result.data?.map { it.toDomain() } ?: emptyList()
                emit(list)
            }
            is Resource.Error -> {emit(emptyList())}
            else -> emit(emptyList())
        }
    }

    override suspend fun getEstadoPropiedades(): Flow<List<EstadoPropiedad>> = flow {
        when(val result = remoteDataSource.getEstadoPropiedades()){
            is Resource.Success -> {
                val list = result.data?.map { it.toDomain() } ?: emptyList()
                emit(list)
            }
            is Resource.Error -> { emit(emptyList()) }
            else -> {emit(emptyList())}
        }
    }
}