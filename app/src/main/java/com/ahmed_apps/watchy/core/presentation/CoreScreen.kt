package com.ahmed_apps.watchy.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ahmed_apps.watchy.auth.util.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun CoreScreen(
    authResultChannel: Flow<AuthResult<Unit>>,
    onAuthorized: () -> Unit,
    onNotAuthorized: () -> Unit
) {
    LaunchedEffect(true) {
        authResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    onAuthorized()
                }

                is AuthResult.SingedOut -> {
                    onNotAuthorized()
                }

                is AuthResult.Unauthorized -> {
                    onNotAuthorized()
                }

                is AuthResult.UnknownError -> {
                    onNotAuthorized()
                }
            }
        }
    }
}

















