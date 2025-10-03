package com.dukeey.whatsapp2.Welcome

import Routes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dukeey.whatsapp2.R

@Composable


fun WellcomeSCRDesign(navController : NavController) {
	var welcome by remember {
		mutableStateOf(true)
	}
	
	
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		
		Image(
			painter = painterResource(R.drawable.welcome),
			contentDescription = "welcome image",
			modifier = Modifier.size(300.dp).padding(bottom = 33.dp)
		)
		
		Text(
			text = "Welcome to Whatsapp",
			fontWeight = FontWeight.Bold,
			fontSize = 23.sp,
			modifier = Modifier.padding(bottom = 23.dp)
		)
		Row {
			Text(text = "Read our", fontSize = 16.sp)
			Spacer(modifier = Modifier.width(2.dp))
			Text(
				text = "Privacy Policy",
				color = colorResource(R.color.light_Green),
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp
			)
			Spacer(modifier = Modifier.width(2.dp))
			Text(
				text = "Tap'",
				fontSize = 16.sp
			)
			Text(
				text = "Agree & Continue' ",
				fontSize = 16.sp
			)
		}
		Row {
			Text(
				text = "to accept the", fontSize = 16.sp)
			Text(text = "Terms of Service", fontWeight = FontWeight.Bold,
				color = colorResource(R.color.light_Green))
		}
		Spacer(modifier = Modifier.height(9.dp))
		Button(onClick = { navController.navigate(Routes.UserloginSCR) },
			colors = ButtonDefaults.buttonColors(
				containerColor = colorResource(R.color.light_Green)),
			modifier = Modifier.width(300.dp),
			shape = RoundedCornerShape(5.dp)
		) {
			Text(text = "Welcome to WhatsApp",
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp)
		}
		
		
	}
	
}
