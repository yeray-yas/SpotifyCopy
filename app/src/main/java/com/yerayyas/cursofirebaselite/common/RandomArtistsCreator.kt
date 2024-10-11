package com.yerayyas.cursofirebaselite.common

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.yerayyas.cursofirebaselite.presentation.model.Artist

fun createArtist(db: FirebaseFirestore) {
    val random = (1..10000).random()
    val artist = Artist(name = "Random $random")
    db.collection("artists")
        .add(artist)
        .addOnSuccessListener {
            Log.i("totoye", "Success")
        }
        .addOnFailureListener {
            Log.i("totoye", "Failure")
        }
        .addOnCompleteListener {
            Log.i("totoye", "Complete")
        }
}