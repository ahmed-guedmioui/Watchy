package com.ahmed_apps.watchy_course.details.di

import com.ahmed_apps.watchy_course.details.data.repository.DetailsRepositoryImpl
import com.ahmed_apps.watchy_course.details.data.repository.VideosRepositoryImpl
import com.ahmed_apps.watchy_course.details.domain.repository.DetailsRepository
import com.ahmed_apps.watchy_course.details.domain.repository.VideosRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @author Ahmed Guedmioui
 */
@Module
@Singleton
abstract class DetailsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindVideosRepository(
        videosRepositoryImpl: VideosRepositoryImpl
    ): VideosRepository

}
























