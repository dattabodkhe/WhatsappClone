package com.dukeey.whatsapp2.homeSCR

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.bottomNevigation.buttomNevigation
import com.dukeey.whatsapp2.chatBox.chatListModel

@Composable
fun homeSCR(navController : NavController) {
	val chatData = listOf(
		chatListitem(
			image = R.drawable.bmw_4,
			name = "mohit hiwale",
			time = "10:00PM",
			message = "hi"
		), chatListitem(
			image = R.drawable.krishna,
			name = "krishna ji",
			message = "hare krishan",
			time = "7:00Am"),
		chatListitem(
			image = R.drawable.shinobo,
			name = "shinobo",
			time = "11:00PM", message = "hi",
		),
		chatListitem(
			image = R.drawable.kakashi,
			name = "kakashi hatake",
			message = "take your mession details",
			time = "7:00Am"
		),
		chatListitem(
			image = R.drawable.obito,
			name = "obito",
			message = "take your mession details",
			time = "7:00Am"
		),
		chatListitem(
			image = R.drawable.bmw_4,
			name = "BMW",
			message = "ready to race",
			time = "7:00Am"
		),
		chatListitem(
			image = R.drawable.krishna,
			name = "krishna",
			message = "hi",
			time = "7:00Am"
		),
		chatListitem(
			image = R.drawable.kakashi,
			name = "kakashi hatake",
			message = "take your mession details",
			time = "7:00Am"
		),
		chatListitem(
			image = R.drawable.kakashi,
			name = "kakashi hatake",
			message = "take your mession details",
			time = "7:00Am"
		),
		chatListitem(
			image = R.drawable.kakashi,
			name = "kakashi hatake",
			message = "take your mession details",
			time = "7:00Am"
		)
		
	
	)
	
	
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = { },
				containerColor = colorResource(id = R.color.light_Green),
				contentColor = colorResource(id = R.color.white)
			) {
				
				Icon(
					painter = painterResource(id = R.drawable.outline_mail_24),
					contentDescription = null
				)
				
				
			}
		},
		bottomBar = {
			buttomNevigation()
		}
	
	) {
		Column(modifier = Modifier.padding(it)) {
			Spacer(modifier = Modifier.height(16.dp))
			
			Box(modifier = Modifier.fillMaxWidth()) {
				
				Text(
					text = "Whatsapp",
					fontSize = 32.sp,
					color = colorResource(id = R.color.light_Green),
					modifier = Modifier.align(Alignment.CenterStart).padding(12.dp),
					fontWeight = FontWeight.Bold
				)
				
				
				
				Row(modifier = Modifier.align(Alignment.CenterEnd)) {
					Spacer(modifier = Modifier.width(110.dp))
					
					IconButton(onClick = {}) {
						Icon(
							painter = painterResource
								(id = R.drawable.outline_photo_camera_24), modifier =
								Modifier.size(32.dp),
							
							contentDescription = null
						)
					}
					Spacer(Modifier.width(15.dp))
					IconButton(onClick = {})
					{
						Icon(
							painter = painterResource
								(id = R.drawable.search_24), modifier =
								Modifier.size(32.dp),
							contentDescription = null
						)
						
					}
					IconButton(onClick = {}) {
						Icon(
							painter = painterResource
								(id = R.drawable.menu_24), modifier =
								Modifier.size(32.dp),
							contentDescription = null
						)
						
					}
				}
			}
			HorizontalDivider()
			
			LazyColumn {
				
				items(chatData) {
					chatListModel(chatListitem=it)
					
				}
			}
		}
	}
}


data class chatListitem(
	val image: Int,
	val name : String,
	val message: String,
	val time : String
)