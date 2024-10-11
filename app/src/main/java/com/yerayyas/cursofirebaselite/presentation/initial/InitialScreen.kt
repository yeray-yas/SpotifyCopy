package com.yerayyas.cursofirebaselite.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yerayyas.cursofirebaselite.R
import com.yerayyas.cursofirebaselite.ui.theme.BackgroundButton
import com.yerayyas.cursofirebaselite.ui.theme.Black
import com.yerayyas.cursofirebaselite.ui.theme.Gray
import com.yerayyas.cursofirebaselite.ui.theme.Green
import com.yerayyas.cursofirebaselite.ui.theme.ShapeButton


@Preview
@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray, Black), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        Logo()

        Spacer(Modifier.weight(1f))

        WelcomeText()

        Spacer(Modifier.weight(1f))

        SignUpButton(onClick = navigateToSignUp)

        Spacer(Modifier.height(8.dp))

        SocialLoginButtons()

        LoginText(onClick = navigateToLogin)

        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(R.drawable.spotify),
        contentDescription = "Spotify logo",
        modifier = Modifier.clip(CircleShape)
    )
}

@Composable
fun WelcomeText() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        WelcomeMessage("Millions of songs.")
        WelcomeMessage("Free on Spotify.")
    }
}

@Composable
fun WelcomeMessage(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    CustomButton(
        text = "Sign up free",
        onClick = onClick,
        backgroundColor = Green,
        textColor = Black
    )
}

@Composable
fun SocialLoginButtons() {
    CustomButton(
        text = "Continue with Google",
        painter = painterResource(R.drawable.google),
        onClick = { /* Google login logic */ }
    )
    Spacer(Modifier.height(8.dp))
    CustomButton(
        text = "Continue with Facebook",
        painter = painterResource(R.drawable.facebook),
        onClick = { /* Facebook login logic */ }
    )
}

@Composable
fun LoginText(onClick: () -> Unit) {
    Text(
        text = "Log In",
        color = Color.White,
        modifier = Modifier
            .padding(24.dp)
            .clickable { onClick() },
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CustomButton(
    text: String,
    painter: Painter? = null,
    onClick: () -> Unit,
    backgroundColor: Color = BackgroundButton,
    textColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(backgroundColor)
            .border(2.dp, color = ShapeButton, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        painter?.let {
            Image(
                painter = it,
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(16.dp)
                    .size(16.dp)
            )
        }
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}
