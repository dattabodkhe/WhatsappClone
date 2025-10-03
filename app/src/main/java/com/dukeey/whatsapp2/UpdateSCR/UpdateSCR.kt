package com.dukeey.whatsapp2.UpdateSCR

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.bottomNevigation.buttomNevigation
import com.dukeey.whatsapp2.chatBox.chatListModel


@Composable
@Preview(showSystemUi = true)

fun Upadate() {
	
	val scrollState = rememberScrollState()
	val sampleStatus = listOf(
		StatusData(image = R.drawable.kakashi, name = "kakashi hatake", time = "10 min ago"),
		StatusData(image = R.drawable.krishna, name = "Vasudev krishn ", time = "12 min ago"),
		StatusData(image = R.drawable.shinobo, name = "shinobu ", time = "45 min ago"),
		StatusData(image = R.drawable.kakashi, name = "might guy", time = "Just now")
	
	)
	val sampleChannel = listOf(
		Channel(image = R.drawable.bmw_4, name = "Bwm", descrition = "life is game"),
		Channel(image = R.drawable.bmw_4, name = "Bwm", descrition = "life is game"),
		Channel(image = R.drawable.bmw_4, name = "chats", descrition = "message is not avaible"),
		Channel(image = R.drawable.whatapp6, name = "insta", descrition = "message is not avaible"),
		Channel(
			image = R.drawable.whatsappp5,
			name = "Whatsapp",
			descrition = "message is not avaible"
		)
	)
	
	Scaffold(floatingActionButton = {
		FloatingActionButton(
			onClick = {},
			containerColor = colorResource(id = R.color.light_Green),
			modifier = Modifier.size(65.dp),
			contentColor = colorResource(id = R.color.white)
		) {
			Icon(
				painter = painterResource(id = R.drawable.outline_photo_camera_24),
				contentDescription = null, modifier = Modifier.size(34.dp),
			)
		}
	}, bottomBar = {
		buttomNevigation()
	},
	topBar = {
		topBar()
	}
	) {
		Column(Modifier.padding(it)
			.fillMaxWidth().verticalScroll(scrollState)) {
			
			Text(text = "Status", fontSize = 20.sp, fontWeight = FontWeight.Bold,
				color = Color.Black,
				modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
			)
			MYstatus()
			sampleStatus.forEach {
			Statusitem(statusData = it)
			}
			Spacer(modifier = Modifier.width(16.dp))
			HorizontalDivider(
				color = Color.Gray)
			
			Text(text = "Channel",
				fontWeight = FontWeight.Bold,
				color = Color.Black,
				fontSize = 20.sp,
				modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
			
			Column(modifier = Modifier.padding(horizontal = 16.dp)) {
				
				Text(text = "stay updated on topics thats matter to you.find Channel to follow bellow")
				
				Spacer(modifier = Modifier.height(32.dp))
				Text(text = "Find channel to follow")
			}
			Spacer(modifier = Modifier.height(16.dp))
			
			sampleChannel.forEach {
				ChannelitemDesgin(channel = it)
			}
		}
	}
}