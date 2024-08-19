package com.example.gympal2.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gympal2.R
import com.example.gympal2.auth.AuthState
import com.example.gympal2.auth.AuthViewModel
import com.example.gympal2.core.ui.form.GetFormField
import com.example.gympal2.core.ui.general.ErrorText
import com.example.gympal2.core.ui.general.LottieAnimationView
import org.koin.androidx.compose.koinViewModel

const val loginNowText = "Already have an account? Click to login"

@Composable
fun RegisterScreen(
    navToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val registerStateHolder = remember { RegisterStateHolderImpl(authViewModel) }

    val screenState by registerStateHolder.state.collectAsState()
    val authSate by authViewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimationView(
            R.raw.auth, modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )

        Spacer(modifier = modifier.height(32.dp))

        GetFormField(screenState.username, "Username")

        GetFormField(screenState.name, "Name")

        GetFormField(screenState.password, "Password", isPassword = true)


        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = loginNowText,
            modifier = modifier.clickable(onClick = navToLogin),
            fontSize = 18.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )

        Spacer(modifier = modifier.height(16.dp))

        Button(onClick = { screenState.onRegisterClicked() }) {
            Text("Register")
        }

        when (authSate) {
            is AuthState.Error -> ErrorText(text = "Error while creating an account ${(authSate as AuthState.Error).message}")
            is AuthState.Success -> Unit
            is AuthState.Loading -> Unit
            is AuthState.Idle -> Unit
        }
    }

}
