package com.ahmed_apps.watchy_course.auth.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ahmed_apps.watchy_course.auth.data.remote.AuthApi
import com.ahmed_apps.watchy_course.auth.domain.usecase.FormValidatorUseCase
import com.ahmed_apps.watchy_course.auth.domain.usecase.ValidateEmailUseCase
import com.ahmed_apps.watchy_course.auth.domain.usecase.ValidateNameUseCase
import com.ahmed_apps.watchy_course.auth.domain.usecase.ValidatePasswordUseCase
import com.ahmed_apps.watchy_course.util.BackendConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * @author Ahmed Guedmioui
 */

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BackendConstants.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPrefs(
        application: Application
    ): SharedPreferences {

        val masterKey = MasterKey.Builder(application)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            application,
            "watchy_course_app_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideFromValidatorUseCase(): FormValidatorUseCase {
        return FormValidatorUseCase(
            ValidateEmailUseCase(),
            ValidatePasswordUseCase(),
            ValidateNameUseCase()
        )
    }

}





















