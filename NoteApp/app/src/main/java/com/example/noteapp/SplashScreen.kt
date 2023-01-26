package com.example.swadeshimithash.presentation.splash_screen

import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.swadeshimithash.R
import com.example.swadeshimithash.feature_sm.domain.util.navigateAndClean
import com.example.swadeshimithash.feature_sm.domain.util.setLightStatusBars
import com.example.swadeshimithash.presentation.util.Screen
import com.example.swadeshimithash.ui.dimens.SDP.sdp_180
import com.example.swadeshimithash.ui.dimens.SDP.sdp_26

@Composable
fun SplashScreen(navController: NavHostController, window: Window) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        window.setLightStatusBars(context, true, R.color.white)
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(sdp_180)
                .padding(sdp_26),
            painter = painterResource(id = R.drawable.ic_splash_screen), contentDescription = ""
        )
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigateAndClean(Screen.OnboardingScreen.route)
        }, 1000)
    }
}