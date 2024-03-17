package com.ahmed_apps.watchy_course.profile.di

import com.ahmed_apps.watchy_course.profile.data.repository.ProfileRepositoryImpl
import com.ahmed_apps.watchy_course.profile.domain.repository.ProfileRepository
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

















