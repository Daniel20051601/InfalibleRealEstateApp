package com.infaliblerealestate.di

import com.infaliblerealestate.data.repository.UsuarioRepositoryImpl
import com.infaliblerealestate.dominio.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGastoRepository(
        impl: UsuarioRepositoryImpl
    ): UsuarioRepository
}