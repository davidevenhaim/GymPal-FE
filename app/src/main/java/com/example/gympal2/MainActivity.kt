package com.example.gympal2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.gympal2.di.appModule
import com.example.gympal2.ui.screen.auth.LoginScreen
import com.example.gympal2.ui.screen.auth.RegisterScreen
import com.example.gympal2.ui.screen.main.HomeScreen
import com.example.gympal2.ui.theme.GymPal2Theme
import com.example.gympal2.viewmodel.AuthState
import com.example.gympal2.viewmodel.AuthViewModel
import com.example.gympal2.viewmodel.WebSocketViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidContext(this@MainActivity)
            modules(listOf(appModule))
        }

        setContent {
            GymPal2Theme {
                AppInit()
            }
        }
    }
}

@Composable
fun AppInit() {
    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.getAuthState()

    val webSocketViewModel: WebSocketViewModel = koinViewModel()
    webSocketViewModel.sendMessage("im sending a message to the websocket")

//    val messages by wsViewModel.messages.collectAsState()

//
//    wsViewModel.sendMessage("hello, im sending message from the websocket")
//
//    messages.forEach { msg -> println("Message received from websocket: $msg") }

    when (authState) {

        is AuthState.Idle -> {
//            AuthScreen(authViewModel)
            MainScreen(authViewModel)
        }

        is AuthState.Success -> {
            MainScreen(authViewModel)
        }

        is AuthState.Error -> {
            AuthScreen(authViewModel)
        }

        is AuthState.Loading -> {
            AuthScreen(authViewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: AuthViewModel) {
    HomeScreen { viewModel.logout() }
}


@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    var isLogin by rememberSaveable { mutableStateOf(true) }

    val toggleIsLogin = { isLogin = !isLogin }

    if (isLogin) {
        LoginScreen(
            { username, password -> viewModel.login(username, password) },
            toggleIsLogin = toggleIsLogin,
        )
    } else {
        RegisterScreen(
            { username, name, password -> viewModel.register(username, name, password) },
            toggleIsLogin = toggleIsLogin,
        )
    }

}
