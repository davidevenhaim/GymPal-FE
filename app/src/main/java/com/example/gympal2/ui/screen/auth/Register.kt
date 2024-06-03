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
import com.example.gympal2.model.RegisterData
import com.example.gympal2.ui.shared.AppTextField
import com.example.gympal2.util.AuthFields
import com.example.gympal2.util.MAX_STRING_LENGTH
import com.example.gympal2.util.MIN_PASSWORD_LENGTH
import com.example.gympal2.util.MIN_STRING_LENGTH
import com.example.gympal2.viewmodel.AuthState

const val loginNowText = "Already have an account? Click to login"

@Composable
fun RegisterScreen(
    onRegister: (String, String, String) -> Unit,
    toggleIsLogin: () -> Unit,
    authState: AuthState,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
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
            label = "Username",
            modifier = modifier.fillMaxWidth(),
            isError = error == AuthFields.Username,
        )

        AppTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name",
            modifier = modifier.fillMaxWidth(),
            isError = error == AuthFields.Name,
        )

        AppTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            modifier = modifier.fillMaxWidth(),
            isError = error == AuthFields.Password,
            isPassword = true
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = loginNowText,
            modifier = modifier.clickable(onClick = toggleIsLogin),
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )

        Spacer(modifier = modifier.height(16.dp))

        Button(
            onClick = {
                val validationResponse = validateForm(
                    RegisterData(username, name, password)
                )
                println("Validation Response is: $validationResponse")
                if (validationResponse == true) {
                    onRegister(username, name, password)
                } else {
                    error = validationResponse.toString()
                }
            },
        ) {
            Text("Register")
        }

        if (error.isNotEmpty()) {
            Text("There is an error in field $error", color = MaterialTheme.colorScheme.error)
        }

    }
}


fun validateForm(data: RegisterData): Any {
    val (username, name, password) = data

    if (username.length < MIN_STRING_LENGTH || username.length > MAX_STRING_LENGTH) {
        return "username"
    }

    if (password.length < MIN_PASSWORD_LENGTH ||
        password.length > MAX_STRING_LENGTH
    ) {
        return "password"
    }

    if (name.length < MIN_STRING_LENGTH || name.length > MAX_STRING_LENGTH) {
        return "name"
    }

    return true
}