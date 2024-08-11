package com.example.gympal2.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gympal2.auth.AuthState
import com.example.gympal2.auth.AuthViewModel
import com.example.gympal2.core.ui.form.GetFormField
import com.example.gympal2.util.HOME_SCREEN
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    toggleIsLogin: () -> Unit,
    state: StateFlow<LoginStateHolder.ScreenState>,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val screenState = state.collectAsState()
    val authState by authViewModel.getAuthState().collectAsState()

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

        GetFormField(screenState.value.username, "Username")

        GetFormField(screenState.value.password, "Password", isPassword = true)

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = "Doesn't have an account? Click to register",
            modifier = modifier.clickable(onClick = toggleIsLogin),
            style = TextStyle(textDecoration = TextDecoration.Underline),
        )

        Spacer(modifier = modifier.height(16.dp))

        Button(onClick = { screenState.value.onLoginClicked() }) {
            Text("Login")
        }
        authViewModel.getAuthState()
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()

            is AuthState.Success -> {
                LaunchedEffect(Unit) {
                    println("Login success. navigating to home screen.")
                    navController.navigate(HOME_SCREEN) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }

            is AuthState.Error -> {
                println("Hello im on error")
                Text((authState as AuthState.Error).message, color = Color.Red)
            }

            AuthState.Idle -> Unit
        }
    }
}
