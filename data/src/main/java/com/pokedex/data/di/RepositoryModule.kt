package com.pokedex.data.di

import com.pokedex.data.error.GeneralErrorHandlerImpl
import com.pokedex.data.repositories.PokedexRepositoryImpl
import com.pokedex.data.repositories.RemoteDataSource
import com.pokedex.data.repositories.RemoteDataSourceImpl
import com.pokedex.domain.error.ErrorHandler
import com.pokedex.domain.repositories.PokedexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePokedexRepoImpl(
        pokedexRepositoryImpl: PokedexRepositoryImpl
    ): PokedexRepository

    @Binds
    abstract fun provideRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun provideErrorHandler(generalErrorHandlerImpl: GeneralErrorHandlerImpl)
    : ErrorHandler
}