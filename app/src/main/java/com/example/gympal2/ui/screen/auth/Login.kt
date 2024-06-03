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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.gympal2.model.LoginData
import com.example.gympal2.ui.shared.AppTextField
import com.example.gympal2.util.AuthFields
import com.example.gympal2.util.MAX_STRING_LENGTH
import com.example.gympal2.util.MIN_PASSWORD_LENGTH
import com.example.gympal2.util.MIN_STRING_LENGTH
import com.example.gympal2.viewmodel.AuthState


@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    toggleIsLogin: () -> Unit,
    authState: AuthState,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

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
            value = username,
            onValueChange = { username = it },
            label = AuthFields.Username,
            modifier = modifier.fillMaxWidth(),
            isError = error == AuthFields.Username,
        )

        AppTextField(
            value = password,
            onValueChange = { password = it },
            label = AuthFields.Password,
            modifier = modifier.fillMaxWidth(),
            isError = error == AuthFields.Password,
            isPassword = true,
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = "Doesn't have an account? Click to register",
            modifier = modifier.clickable(onClick = toggleIsLogin),
            style = TextStyle(textDecoration = TextDecoration.Underline),
        )

        Spacer(modifier = modifier.height(16.dp))

        Button(
            onClick = {
                val validationResponse =
                    validateForm(LoginData(username, password))
                println("IM here $validationResponse")

                if (validationResponse == true) {
                    print("On login will be called.")
                    onLogin(username, password)
                } else {
                    error = validationResponse.toString()
                }
            },
        ) {
            Text("Login")
        }
        if (error.isNotEmpty()) {
            Text("There is an error in field $error", color = MaterialTheme.colorScheme.error)
        }
    }
}


fun validateForm(data: LoginData): Any {
    val (username, password) = data

    if (username.length < MIN_STRING_LENGTH || username.length > MAX_STRING_LENGTH) {
        return AuthFields.Username
    }

    if (password.length < MIN_PASSWORD_LENGTH ||
        password.length > MAX_STRING_LENGTH
    ) {
        return AuthFields.Password
    }

    return true
}
