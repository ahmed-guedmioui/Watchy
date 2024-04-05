package com.ahmed_apps.watchy.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed_apps.watchy.R
import com.ahmed_apps.watchy.auth.util.AuthResult
import kotlinx.coroutines.flow.collectLatest

/**
 * @author Ahmed Guedmioui
 */
@Composable
fun RegisterScreen(
    onAuthorized: () -> Unit,
    onLoginClick: () -> Unit,
) {

    val registerViewModel = hiltViewModel<RegisterViewModel>()
    val registerState by registerViewModel.registerState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(true) {
        registerViewModel.authResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    onAuthorized()
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.unknown_error_happened),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResult.SingedOut -> {}
                is AuthResult.Unauthorized -> {}
            }
        }
    }

    LaunchedEffect(true) {
        registerViewModel.invalidCredentialsChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context,
                    context.getString(R.string.invalid_credentials),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(30.dp),
            painter = painterResource(R.drawable.icon),
            contentDescription = stringResource(R.string.app_icon)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.create_your_account),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.register),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = registerState.name,
            onValueChange = {
                registerViewModel.onEvent(
                    RegisterUiEvents.OnNameChanged(it)
                )
            },
            label = {
                Text(text = stringResource(R.string.name))
            },
            textStyle = TextStyle(fontSize = 15.sp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = registerState.email,
            onValueChange = {
                registerViewModel.onEvent(
                    RegisterUiEvents.OnEmailChanged(it)
                )
            },
            label = {
                Text(text = stringResource(R.string.email))
            },
            textStyle = TextStyle(fontSize = 15.sp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        var passwordVisibility by rememberSaveable {
            mutableStateOf(false)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = registerState.password,
            onValueChange = {
                registerViewModel.onEvent(
                    RegisterUiEvents.OnPasswordChanged(it)
                )
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            textStyle = TextStyle(fontSize = 15.sp),
            maxLines = 1,
            visualTransformation =
            if (passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            passwordVisibility = !passwordVisibility
                        },
                    imageVector =
                    if (passwordVisibility) Icons.Rounded.Visibility
                    else Icons.Rounded.VisibilityOff,
                    contentDescription =
                    if (passwordVisibility)
                        stringResource(R.string.password_is_visible)
                    else
                        stringResource(R.string.password_is_invisible),
                )
            }
        )

        Spacer(modifier = Modifier.height(22.dp))

        if (registerState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    registerViewModel.onEvent(
                        RegisterUiEvents.Register
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.already_have_an_account),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(R.string.login),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onLoginClick() }
            )

        }

    }

}





















