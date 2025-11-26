package com.infaliblerealestate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.infaliblerealestate.data.local.dao.UsuarioDao
import com.infaliblerealestate.data.local.entities.UsuarioEntity

@Database(
    entities = [UsuarioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}