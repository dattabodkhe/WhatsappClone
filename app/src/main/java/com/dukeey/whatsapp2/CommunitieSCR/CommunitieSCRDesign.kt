package com.dukeey.whatsapp2.CommunitieSCR

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
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


@Composable
fun ComScreenDesign(communities: Communities) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(12.dp)
	) {
		Image(
			painter = painterResource(id = communities.imageRes),
			contentDescription = communities.name,
			modifier = Modifier.size(50.dp)
		)
		Spacer(modifier = Modifier.width(12.dp))
		Column {
			Text(text = communities.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
			Text(text = communities.members, fontSize = 13.sp, color = Color.Gray)
		}
	}
}

@Composable
@Preview(showSystemUi = true)
fun communitiesSCRDesign() {
	
	var isSearching by remember { mutableStateOf(false) }
	var search by remember { mutableStateOf("") }
	var showmenu by remember { mutableStateOf(false) }
	
	val sampleCommunities = listOf(
		Communities(R.drawable.bmw_4, "BMW", "7.2b members"),
		Communities(R.drawable.bmw_4, "BMW", "7.2b members"),
		Communities(R.drawable.shinobo, "Anime Gs", "7.2b members"),
		Communities(R.drawable.kakashi, "Leaf Village", "2b members"),
		Communities(R.drawable.whatsappp5, "WhatsApp", "2.2b members")
	)
	
	Scaffold(
		topBar = {
			Box(modifier = Modifier.fillMaxWidth()) {
				Column {
					Row {
						if (isSearching) {
							TextField(
								value = search,
								onValueChange = { search = it },
								placeholder = { Text(text = "Search") },
								colors = TextFieldDefaults.colors(
									unfocusedContainerColor = Color.Transparent,
									focusedContainerColor = Color.Transparent,
									unfocusedIndicatorColor = Color.Transparent,
									focusedIndicatorColor = Color.Transparent
								),
								modifier = Modifier.padding(start = 12.dp),
								singleLine = true
							)
						} else {
							Text(
								text = "Communities",
								fontSize = 27.sp,
								fontWeight = FontWeight.Bold,
								modifier = Modifier.padding(start = 12.dp, top = 12.dp)
							)
						}
						
						Spacer(modifier = Modifier.weight(1f))
						
						if (isSearching) {
							IconButton(onClick = { isSearching = false }) {
								Icon(
									painter = painterResource(R.drawable.cancel_24),
									contentDescription = "cancel",
									modifier = Modifier.size(23.dp)
								)
							}
						} else {
							IconButton(onClick = { isSearching = true }) {
								Icon(
									painter = painterResource(R.drawable.search_24),
									contentDescription = "search",
									modifier = Modifier.size(28.dp)
								)
							}
							IconButton(onClick = { showmenu = true }) {
								Icon(
									painter = painterResource(R.drawable.menu_24),
									contentDescription = "menu",
									modifier = Modifier.size(28.dp)
								)
								DropdownMenu(
									expanded = showmenu,
									onDismissRequest = { showmenu = false }
								) {
									DropdownMenuItem(
										text = { Text(text = "Status settings") },
										onClick = { showmenu = false }
									)
									DropdownMenuItem(
										text = { Text(text = "Create Channel") },
										onClick = { showmenu = false }
									)
									DropdownMenuItem(
										text = { Text(text = "Settings") },
										onClick = { showmenu = false }
									)
								}
							}
						}
					}
					HorizontalDivider()
				}
			}
		},
		bottomBar = { buttomNevigation() }
	) {
		Column(modifier = Modifier.padding(it)) {
			Button(
				onClick = {},
				colors = ButtonDefaults.buttonColors(
					containerColor = colorResource(id = R.color.light_Green)
				),
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) {
				Text(
					text = "Start a new Community",
					fontWeight = FontWeight.Bold,
					fontSize = 16.sp
				)
			}
			
			Spacer(modifier = Modifier.height(8.dp))
			
			Text(
				text = "Your Communities",
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
			)
			
			LazyColumn {
				items(sampleCommunities) {
					ComScreenDesign(communities = it)
				}
			}
		}
	}
}
