package com.yerayyas.cursofirebaselite.presentation.auth

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun signIn(email: String, password: String, callback: (Result<FirebaseUser?>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                handleSignInResult(task, callback)
            }
    }

    fun signOut(callback: (Result<Unit>) -> Unit) {
        auth.signOut()
        callback(Result.success(Unit)) // Assuming signOut is always successful
    }

    private fun handleSignInResult(task: Task<AuthResult>, callback: (Result<FirebaseUser?>) -> Unit) {
        if (task.isSuccessful) {
            val user = task.result?.user // This is the FirebaseUser
            callback(Result.success(user)) // Pass the user to the callback
        } else {
            callback(Result.failure(task.exception ?: Exception("Login failed"))) // Handle the error
        }
    }
}
