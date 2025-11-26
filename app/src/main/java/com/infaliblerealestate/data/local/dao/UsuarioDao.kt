package com.infaliblerealestate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infaliblerealestate.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getUsuario(id: String): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuarios LIMIT 1")
    fun getUsuarioActual(): Flow<UsuarioEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: UsuarioEntity)

    @Query("DELETE FROM usuarios")
    suspend fun deleteAllUsuarios()

    @Query("SELECT lastSync FROM usuarios WHERE id = :id")
    suspend fun getLastSync(id: String): Long?
}