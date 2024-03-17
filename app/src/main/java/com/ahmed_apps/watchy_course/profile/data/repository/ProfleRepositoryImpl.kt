package com.ahmed_apps.watchy_course.profile.data.repository

import android.app.Application
import android.content.SharedPreferences
import com.ahmed_apps.watchy_course.R
import com.ahmed_apps.watchy_course.profile.domain.repository.ProfileRepository
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class ProfileRepositoryImpl @Inject constructor(
    private val application: Application,
    private val prefs: SharedPreferences
): ProfileRepository {
    override suspend fun getName(): String {
        return prefs.getString("name", null)
            ?: application.getString(R.string.name)
    }

    override suspend fun getEmail(): String {
        return prefs.getString("email", null)
            ?: application.getString(R.string.email)
    }
}




















