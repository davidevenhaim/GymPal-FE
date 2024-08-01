package com.example.gympal2.ui.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.gympal2.model.LoginData
import com.example.gympal2.ui.shared.AppTextField
import com.example.gympal2.util.AuthFields
import com.example.gympal2.util.ValidationResult
import com.example.gympal2.util.validateLoginForm
import com.example.gympal2.viewmodel.AuthViewModel
import org.koin.androidx.compose.getViewModel
import androidx.navigation.NavController
import com.example.gympal2.data.repository.TokenManager
import com.example.gympal2.util.HOME_SCREEN
import com.example.gympal2.viewmodel.AuthState


@Composable
fun LoginScreen(
    navController: NavController,
    toggleIsLogin: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = getViewModel()
) {
    val authState by authViewModel.getAuthState()

    var formState by remember { mutableStateOf(LoginData()) }
    var usernameError by remember { mutableStateOf<ValidationResult?>(null) }
    var passwordError by remember { mutableStateOf<ValidationResult?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Rounded.Face,
            contentDescription = "Face",
            modifier = modifier.size(54.dp)
        )

        Spacer(modifier = modifier.height(32.dp))

        AppTextField(
            value = formState.username,
            onValueChange = { formState = formState.copy(username = it) },
            label = AuthFields.Username,
            modifier = modifier.fillMaxWidth(),
            isError = usernameError?.isValid == false,
        )
        if (usernameError?.isValid == false) {
            ErrorText(text = usernameError!!.errorMessage!!)
        }

        AppTextField(
            value = formState.password,
            onValueChange = { formState = formState.copy(password = it) },
            label = AuthFields.Password,
            modifier = modifier.fillMaxWidth(),
            isError = passwordError?.isValid == false,
            isPassword = true,
        )
        if (passwordError?.isValid == false) {
            ErrorText(text = passwordError!!.errorMessage!!)
        }

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = "Doesn't have an account? Click to register",
            modifier = modifier.clickable(onClick = toggleIsLogin),
            style = TextStyle(textDecoration = TextDecoration.Underline),
        )

        Spacer(modifier = modifier.height(16.dp))

        Button(
            onClick = {
                val validationErrors = validateLoginForm(formState)
                usernameError = validationErrors["username"]
                passwordError = validationErrors["password"]

                if (validationErrors.values.all { it.isValid }) {
                    authViewModel.login(formState.username, formState.password)
                }
            },
        ) {
            Text("Login")
        }
        authViewModel.getAuthState()

        when(authState) {
            is AuthState.Loading -> CircularProgressIndicator()

            is AuthState.Success -> {
                LaunchedEffect(Unit) {

                    navController.navigate(HOME_SCREEN) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }

            is AuthState.Error -> {
                Text((authState as AuthState.Error).message, color = Color.Red)
            }

            AuthState.Idle ->  Unit
        }
    }
}