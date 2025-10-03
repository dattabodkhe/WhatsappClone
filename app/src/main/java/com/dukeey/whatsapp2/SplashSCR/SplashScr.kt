package com.dukeey.whatsapp2.SplashSCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dukeey.whatsapp2.R
import kotlinx.coroutines.delay

@Composable


fun SplaschSCR(navController : NavController) {
LaunchedEffect(Unit) {
	delay(1000)
	navController.navigate(Routes.WelcomeSCR){
		popUpTo(Routes.SplashSCR)
	}
	
}
	
	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			painter = painterResource
				(id = R.drawable.whatapp6),
			contentDescription = null, modifier = Modifier.size(228.dp)
				.align(Alignment.Center)
		)
		
	}
}
