package com.dukeey.whatsapp2.bottomNevigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dukeey.whatsapp2.R

@Composable
@Preview(showSystemUi = true)

fun buttomNevigation(){
	
	BottomAppBar(tonalElevation = 12.dp, contentColor = Color.Black) {
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
			
			Column(
				modifier = Modifier.padding(horizontal = 16.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Icon(
					painter = painterResource(id = R.drawable.chat_24),
					contentDescription = null, modifier = Modifier.size(30.dp)
				)
				Spacer(modifier = Modifier.height(2.dp))
				
				Text(text = "Chat", fontWeight = FontWeight.Bold)
			}
			
			Column(
				modifier = Modifier.padding(horizontal = 16.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			)
			{
				
				Icon(
					painter = painterResource(id = R.drawable.update_24),
					contentDescription = null,
					modifier = Modifier.size(30.dp)
				)
				Spacer(modifier = Modifier.height(2.dp))
				
				Text("Update", fontWeight = FontWeight.Bold)
				
			}
			Column(
				modifier = Modifier.padding(horizontal = 16.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			)
			{
				Icon(
					painter = painterResource(id = R.drawable.communities_24,),
					contentDescription = null, modifier = Modifier.size(30.dp)
				)
				Spacer(modifier = Modifier.height(2.dp))
				Text(text = "communities", fontWeight = FontWeight.Bold)
				
				
			}
			Column (modifier = Modifier.padding(horizontal = 16.dp),
				horizontalAlignment = Alignment.CenterHorizontally)
			{
				Icon(painter = painterResource(id = R.drawable.outline_call_24,),
					contentDescription = null, modifier = Modifier.size(30.dp)
				)
				Spacer(modifier = Modifier.height(2.dp))
				Text(text = "Calls", fontWeight = FontWeight.Bold)
				
				
				
				
			}
			
			
		}
		
		
	}
}

