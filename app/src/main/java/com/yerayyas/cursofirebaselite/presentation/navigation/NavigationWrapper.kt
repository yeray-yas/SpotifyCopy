package com.yerayyas.cursofirebaselite.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.yerayyas.cursofirebaselite.presentation.auth.AuthViewModel
import com.yerayyas.cursofirebaselite.presentation.home.HomeScreen
import com.yerayyas.cursofirebaselite.presentation.initial.InitialScreen
import com.yerayyas.cursofirebaselite.presentation.login.LoginScreen
import com.yerayyas.cursofirebaselite.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    authViewModel: AuthViewModel
) {
    // Redirect to "home" if there is an authenticated user
    LaunchedEffect(key1 = auth.currentUser) {
        if (auth.currentUser != null) {
            navHostController.navigate("home") {
                popUpTo("initial") { inclusive = true }
            }
        }
    }

    // Navigation routes' configuration
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") }
            )
        }

        composable("logIn") {
            LoginScreen(authViewModel) {
                navHostController.navigate("home") {
                    popUpTo("logIn") { inclusive = true }
                }
            }
        }

        composable("signUp") {
            SignUpScreen(auth)
        }

        composable("home") {
            HomeScreen(
                viewModel = viewModel(),
                authViewModel = authViewModel,
                navigateToLogin = {
                    navHostController.navigate("logIn") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
