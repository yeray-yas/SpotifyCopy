package com.yerayyas.cursofirebaselite.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yerayyas.cursofirebaselite.presentation.auth.AuthViewModel
import com.yerayyas.cursofirebaselite.presentation.navigation.NavigationWrapper
import com.yerayyas.cursofirebaselite.ui.theme.CursoFirebaseLiteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFirebaseAuth()
        setupUI()
    }

    private fun initializeFirebaseAuth() {
        auth = Firebase.auth
    }

    private fun setupUI() {
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            CursoFirebaseLiteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationWrapper(navHostController, auth, authViewModel)
                }
            }
        }
    }
}

