package com.example.gympal2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.gympal2.data.repository.TokenManager
import com.example.gympal2.di.appModule
import com.example.gympal2.ui.screen.auth.LoginScreen
import com.example.gympal2.ui.screen.auth.RegisterScreen
import com.example.gympal2.ui.screen.main.HomeScreen
import com.example.gympal2.ui.theme.GymPal2Theme
import com.example.gympal2.viewmodel.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gympal2.util.AUTH_SCREEN
import com.example.gympal2.util.HOME_SCREEN
import org.koin.android.ext.android.inject



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tokenManager: TokenManager by inject()

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(listOf(appModule))
        }

        setContent {
            GymPal2Theme {
                AppInit(tokenManager)
            }
        }
    }
}

@Composable
fun AppInit(tokenManager: TokenManager) {
    val authViewModel: AuthViewModel = koinViewModel()
    val navController = rememberNavController()

    val startDestination = if (tokenManager.getToken() != null) HOME_SCREEN else AUTH_SCREEN

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
fun AuthScreen(viewModel: AuthViewModel, navController: NavHostController) {

    var isLogin by rememberSaveable { mutableStateOf(true) }

    val toggleIsLogin = { isLogin = !isLogin }

    if (isLogin) {
        LoginScreen(navController, toggleIsLogin = toggleIsLogin)
    } else {
        RegisterScreen(navController, toggleIsLogin = toggleIsLogin,)
    }

}
