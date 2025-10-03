package com.dukeey.whatsapp2.callSCR

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.bottomNevigation.buttomNevigation

@Composable
fun CallScr() {
	val smapleCall=listOf(
		Call(image = R.drawable.shinobo, name = "shinobo", time = "12:00pm", isMissed = true),
		Call(image = R.drawable.kakashi, name = "Kakashi ", time = "3:00Am", isMissed =false),
		Call(image = R.drawable.krishna, name = "krishn ji", time = "2:00pm", isMissed = false),
		Call(image = R.drawable.obito, name = "Obito", time = "6:00pm", isMissed = false),
		Call(image = R.drawable.shinobo, name = "shinobo", time = "12:00pm", isMissed = true),
		Call(image = R.drawable.shinobo, name = "shinobo", time = "12:00pm", isMissed = true),
		Call(image = R.drawable.shinobo, name = "shinobo", time = "12:00pm", isMissed = true)
	)
	var isSearching by remember {
		mutableStateOf(true)
	}
	var Search by remember {
		mutableStateOf("")
	}
	var Showmanu by remember {
		mutableStateOf(false)
	}
	
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = {},
				containerColor = colorResource(R.color.light_Green),
				contentColor = Color.White
			) {
				Icon(
					painter = painterResource(id = R.drawable.outline_call_24),
					contentDescription = "calls",
					modifier = Modifier.size(26.dp),
				)
			}
		}, bottomBar = {
			buttomNevigation()
		}
	
	) {Column(modifier = Modifier.padding(it)) {
		Spacer(modifier = Modifier.height(22.dp))
		FavSeaction()
		
		
		Button(onClick = {},
			colors = ButtonDefaults.buttonColors(
				containerColor = colorResource(R.color.light_Green)),
			modifier = Modifier.fillMaxWidth().padding(16.dp)) {
			Text(text = "Start a new call", fontWeight = FontWeight.Bold,
				fontSize = 14.sp)
		}
		Text(text = "Recent Calls", fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
		LazyColumn {
			items(smapleCall){data->
				CallitemDesign(data)
			}
		}
	}
	}
	Box(modifier = Modifier.fillMaxWidth())
	{
		Column {
			Row {
				if (isSearching) {
					Text(
						text = "Call",
						fontSize = 28.sp, color = Color.Black,
						fontWeight = FontWeight.Bold
					)
				} else {
					TextField(
						value = Search, onValueChange = { Search = it }, placeholder = {
							Text(text = "Search")
						}, colors = TextFieldDefaults.colors(
							unfocusedContainerColor = Color.Transparent,
							focusedContainerColor = Color.Transparent,
							unfocusedIndicatorColor = Color.Transparent,
							focusedIndicatorColor = Color.Transparent
						), singleLine = true, modifier = Modifier.padding(start = 8.dp)
					)
					
					
				}
				Spacer(modifier = Modifier.weight(1f))
				if (isSearching) {
					IconButton(onClick = { isSearching }) {
						Icon(
							painter = painterResource(id = R.drawable.search_24),
							contentDescription = null,
							modifier = Modifier.size(28.dp)
						)
					}
					
					IconButton(onClick = {}) {
						Icon(
							painter = painterResource(id = R.drawable.menu_24),
							contentDescription = null,
							modifier = Modifier.size(28.dp)
						)
						DropdownMenu(expanded = Showmanu, onDismissRequest = { Showmanu = true }) {
							DropdownMenuItem(
								text = { Text(text = "Clean histroy") },
								onClick = { Showmanu = true })
							DropdownMenuItem(
								text = { Text(text = "Settings") },
								onClick = { Showmanu = true })
						}
						
					}
					
					
				} else {
					IconButton(onClick = {
						isSearching = true
						Search = ""
					}) {
						Icon(
							painter = painterResource(id = R.drawable.cancel_24),
							contentDescription = null,
							modifier = Modifier.size(28.dp)
						)
					}
				}
				
			}
			HorizontalDivider()
			
			
		}
		
	}
	
	
	
}
