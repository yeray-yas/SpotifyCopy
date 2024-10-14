package com.yerayyas.cursofirebaselite.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yerayyas.cursofirebaselite.CursoFirebaseLiteApp.Companion.context
import com.yerayyas.cursofirebaselite.data.Repository
import com.yerayyas.cursofirebaselite.domain.usecases.CanAccessToAppUseCase
import com.yerayyas.cursofirebaselite.presentation.model.Artist
import com.yerayyas.cursofirebaselite.presentation.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val repository = Repository(context)
    private val canAccessToAppUseCase = CanAccessToAppUseCase(repository)
    private val realtimeDatabase = Firebase.database
    private val db: FirebaseFirestore = Firebase.firestore

    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist: StateFlow<List<Artist>> = _artist

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player

    private val _blockVersion = MutableStateFlow(false)
    val blockVersion: StateFlow<Boolean> = _blockVersion

    init {
        checkUserVersion()
        getArtists()
        getPlayer()
    }

    private fun checkUserVersion() {
        viewModelScope.launch {
            _blockVersion.value = withContext(Dispatchers.IO) {
                !canAccessToAppUseCase()
            }
        }
    }

    private fun getPlayer() {
        viewModelScope.launch {
            collectPlayer().collect { snapshot ->
                val player = snapshot.getValue(Player::class.java)
                _player.value = player
            }
        }
    }

    private fun getArtists() {
        viewModelScope.launch {
            _artist.value = withContext(Dispatchers.IO) { getAllArtists() }
        }
    }

    private suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Artist::class.java) }
        } catch (e: Exception) {
            Log.i("totoyeye", e.toString())
            emptyList()
        }
    }

    private fun collectPlayer(): Flow<DataSnapshot> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("totoyeye", "Error: ${error.message}")
                close(error.toException())
            }
        }

        val ref = realtimeDatabase.reference.child("player")
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    fun onPlaySelected() {
        player.value?.let {
            val currentPlayer = it.copy(play = !it.play!!)
            realtimeDatabase.reference.child("player").setValue(currentPlayer)
        }
    }

    fun onCancelSelected() {
        realtimeDatabase.reference.child("player").setValue(null)
    }

    fun addPlayer(artist: Artist) {
        val player = Player(artist = artist, play = true)
        realtimeDatabase.reference.child("player").setValue(player)
    }
}


