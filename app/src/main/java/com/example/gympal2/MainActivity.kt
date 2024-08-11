package com.example.gympal2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gympal2.auth.AuthState
import com.example.gympal2.auth.AuthViewModel
import com.example.gympal2.auth.login.LoginScreen
import com.example.gympal2.auth.login.LoginStateHolderImpl
import com.example.gympal2.auth.register.RegisterScreen
import com.example.gympal2.auth.register.RegisterStateHolderImpl
import com.example.gympal2.core.ui.theme.GymPal2Theme
import com.example.gympal2.util.AUTH_SCREEN
import com.example.gympal2.util.HOME_SCREEN
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidLogger()
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
    val navController = rememberNavController()
    val authState by authViewModel.getAuthState().collectAsState()

    val startDestination = when (authState) {
        is AuthState.Success -> HOME_SCREEN
        is AuthState.Error -> AUTH_SCREEN
        AuthState.Idle -> AUTH_SCREEN
        AuthState.Loading -> AUTH_SCREEN
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(AUTH_SCREEN) { AuthScreen(authViewModel, navController) }
        composable(HOME_SCREEN) { MainScreen(authViewModel, navController) }
    }
}


@Composable
fun MainScreen(viewModel: AuthViewModel, navController: NavHostController) {
    HomeScreen {
        viewModel.logout()
        navController.navigate(HOME_SCREEN)
    }
}


@Composable
fun AuthScreen(authViewModel: AuthViewModel, navController: NavHostController) {

    var isLogin by rememberSaveable { mutableStateOf(true) }
    val loginStateHolder = remember { LoginStateHolderImpl(authViewModel) }
    val registerStateHolder = remember { RegisterStateHolderImpl(authViewModel) }

    val toggleIsLogin = {
        isLogin = !isLogin
    }

    if (isLogin) {
        LoginScreen(navController, toggleIsLogin = toggleIsLogin, loginStateHolder.state)
    } else {
        RegisterScreen(navController, toggleIsLogin = toggleIsLogin, registerStateHolder.state)
    }

}
