package com.yerayyas.cursofirebaselite.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yerayyas.cursofirebaselite.presentation.auth.AuthViewModel
import com.yerayyas.cursofirebaselite.presentation.components.ArtistItem
import com.yerayyas.cursofirebaselite.presentation.components.PlayerComponent
import com.yerayyas.cursofirebaselite.presentation.dialogs.DialogUpdate
import com.yerayyas.cursofirebaselite.presentation.model.Artist
import com.yerayyas.cursofirebaselite.presentation.model.Player
import com.yerayyas.cursofirebaselite.ui.theme.Black



@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel,
    navigateToLogin: () -> Unit
) {
    val artists = viewModel.artist.collectAsState()
    val player by viewModel.player.collectAsState()
    val blockVersion by viewModel.blockVersion.collectAsState()

    if (blockVersion) {
        val context = LocalContext.current
        DialogUpdate(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        HomeTopAppBar(authViewModel, navigateToLogin)
        HomeContent(artists.value, player, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(authViewModel: AuthViewModel, navigateToLogin: () -> Unit) {
    TopAppBar(
        title = { Text("Home") },
        actions = {
            IconButton(onClick = {
                authViewModel.signOut { result ->
                    if (result.isSuccess) {
                        navigateToLogin()
                    } else {
                        Log.e("HomeScreen", "Error signing out: ${result.exceptionOrNull()?.message}")
                    }
                }
            }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Log Out")
            }
        }
    )
}

@Composable
fun HomeContent(artists: List<Artist>, player: Player?, viewModel: HomeViewModel = hiltViewModel()) {
    Text(
        text = "Popular Artists",
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        modifier = Modifier.padding(16.dp)
    )

    LazyRow {
        items(artists) { artist ->
            ArtistItem(
                artist = artist,
                onItemSelected = { viewModel.addPlayer(artist) }
            )
        }
    }
    Spacer(modifier = Modifier.height(300.dp))
    if (player != null) {
        PlayerComponent(
            player = player,
            onPlaySelected = { viewModel.onPlaySelected() },
            onCancelSelected = { viewModel.onCancelSelected() }
        )
    }
}

fun navigateToPlayStore(context: Context) {
    val appPackage = context.packageName
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackage")))
    } catch (e: Exception) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")
            )
        )
    }
}

