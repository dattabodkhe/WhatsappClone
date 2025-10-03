package com.dukeey.whatsapp2.callSCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable


fun FavouriteItem(favContact : FavContact){
	
	Column (horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier.padding(start = 4.dp, top = 7.dp, end = 12.dp)){
		Image(painter = painterResource(favContact.image),
			contentDescription = "imange",
			modifier = Modifier.size(66.dp).clip(shape = CircleShape),
			contentScale = ContentScale.Crop
		)
		Spacer(modifier = Modifier.height(4.dp))
		Text(text = favContact.name, fontWeight = FontWeight.Bold,
			fontSize = 14.sp
		)
	}
	
	
}
