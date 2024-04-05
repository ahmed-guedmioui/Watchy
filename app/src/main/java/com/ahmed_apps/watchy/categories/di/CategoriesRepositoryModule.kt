package com.ahmed_apps.watchy.categories.di

import com.ahmed_apps.watchy.categories.data.repository.CategoriesRepositoryImpl
import com.ahmed_apps.watchy.categories.domain.repository.CategoriesRepository
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
abstract class CategoriesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository

}
























