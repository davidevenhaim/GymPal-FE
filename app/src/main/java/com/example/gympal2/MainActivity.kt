package com.example.gympal2

import NetworkUtil
import NoInternetConnectionDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.gympal2.auth.AuthState
import com.example.gympal2.auth.AuthViewModel
import com.example.gympal2.auth.login.LoginScreen
import com.example.gympal2.auth.register.RegisterScreen
import com.example.gympal2.core.ui.theme.GymPal2Theme
import com.example.gympal2.feature.workout.WorkoutFormScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

const val HOME_SCREEN = "home"
const val AUTH_SCREEN = "auth"
const val LOGIN_SCREEN = "login"
const val REGISTER_SCREEN = "register"
const val MAP_SCREEN = "map"
const val NEW_WORKOUT_SCREEN = "new_workout"

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
    val context = LocalContext.current
    val networkUtil = NetworkUtil(context)

    val authState by authViewModel.state.collectAsState()
    val isOnline by networkUtil.isOnlineState.collectAsState()
    var showDialog by remember { mutableStateOf(true) } // Initial state to show the dialog


    val startDestination = when (authState) {
        is AuthState.Success -> HOME_SCREEN
        is AuthState.Error -> AUTH_SCREEN
        is AuthState.Idle -> AUTH_SCREEN
        is AuthState.Loading -> AUTH_SCREEN
    }

    if (showDialog && !isOnline) {
        NoInternetConnectionDialog(
            onDismiss = { showDialog = false },
            canDismiss = startDestination == HOME_SCREEN
        )
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        navigation(startDestination = MAP_SCREEN, route = HOME_SCREEN) {
            composable(MAP_SCREEN) { HomeScreen(navController) { authViewModel.logout() } }
            composable("$NEW_WORKOUT_SCREEN/{gymId}") { navBackStackEntry ->
                val gymId = navBackStackEntry.arguments?.getString("gymId")
                WorkoutFormScreen(currentGymId = gymId ?: "", navController)
            }
        }

        navigation(startDestination = LOGIN_SCREEN, route = AUTH_SCREEN) {
            composable(LOGIN_SCREEN) { LoginScreen({ navController.navigate(REGISTER_SCREEN) }) }
            composable(REGISTER_SCREEN) { RegisterScreen({ navController.navigateUp() }) }
        }
    }
}
