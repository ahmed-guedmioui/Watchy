package com.ahmed_apps.watchy.search.di

import com.ahmed_apps.watchy.search.data.repository.SearchRepositoryImpl
import com.ahmed_apps.watchy.search.domain.repository.SearchRepository
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
abstract class SearchRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
       searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

}























