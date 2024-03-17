package com.ahmed_apps.watchy_course.profile.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed_apps.watchy_course.R
import com.ahmed_apps.watchy_course.auth.util.AuthResult
import kotlinx.coroutines.flow.collectLatest

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {

    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val profileState by profileViewModel.profileState.collectAsState()

    LaunchedEffect(true) {
        profileViewModel.logoutResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.SingedOut -> {
                    onLogout()
                }

                is AuthResult.Authorized -> Unit
                is AuthResult.Unauthorized -> Unit
                is AuthResult.UnknownError -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.profile),
                    fontSize = 18.sp
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp)
                .padding(top = paddingValues.calculateTopPadding())
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profileState.name.take(1),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 80.sp
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            UserInfo(
                title = stringResource(R.string.name),
                value = profileState.name
            )

            Spacer(modifier = Modifier.height(32.dp))

            UserInfo(
                title = stringResource(R.string.email),
                value = profileState.email
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    profileViewModel.onEvent(
                        ProfileUiEvents.Logout
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 70.dp)
            ) {

                Text(
                    text = stringResource(R.string.log_out)
                )

            }

        }

    }

}

@Composable
fun UserInfo(
    title: String,
    value: String
) {

    Text(
        text = title,
        color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
        fontSize = 16.sp
    )

    Spacer(modifier = Modifier.height(6.dp))

    Text(
        text = value,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

}
















