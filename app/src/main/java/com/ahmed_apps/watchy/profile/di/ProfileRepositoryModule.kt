package com.ahmed_apps.watchy.profile.di

import com.ahmed_apps.watchy.profile.data.repository.ProfileRepositoryImpl
import com.ahmed_apps.watchy.profile.domain.repository.ProfileRepository
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
abstract class ProfileRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
       profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

}

















