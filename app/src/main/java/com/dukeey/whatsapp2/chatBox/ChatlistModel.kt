package com.dukeey.whatsapp2.chatBox

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.homeSCR.chatListitem

@Composable

fun chatListModel(chatListitem : chatListitem){
	
	
	
	Row (modifier = Modifier.padding(11.dp),
		verticalAlignment = Alignment.CenterVertically)
	{
		Image(painter = painterResource(id=chatListitem.image)
			, contentDescription = null,
			modifier = Modifier
				.size(55.dp)
				.clip(shape = CircleShape),
			contentScale = ContentScale.Crop
		
		)
		Spacer(modifier = Modifier.height(24.dp))
		
		Column(modifier = Modifier.padding(start = 12.dp) ) {
			Row(horizontalArrangement = Arrangement
				.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
				
				Text(text = chatListitem.name,
					fontWeight = FontWeight.Bold,
					fontSize = 23.sp)
				
				Text(text = chatListitem.time, color = Color.Gray)
			}
			Spacer(modifier = Modifier.height(4.dp))
			Text(text= chatListitem.message, fontSize = 14.sp,
				color = Color.Gray, fontWeight = FontWeight.SemiBold)
		}
		
		
	}
}
