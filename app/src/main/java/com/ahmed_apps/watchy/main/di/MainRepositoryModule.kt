package com.ahmed_apps.watchy.main.di

import com.ahmed_apps.watchy.main.data.repository.MainRepositoryImpl
import com.ahmed_apps.watchy.main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Ahmed Guedmioui
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

}























