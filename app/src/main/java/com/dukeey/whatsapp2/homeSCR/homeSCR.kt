package com.dukeey.whatsapp2.homeSCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.ViewModel.baseViewModel
import com.dukeey.whatsapp2.bottomNevigation.buttomNevigation
import com.google.firebase.auth.FirebaseAuth

@Composable
fun homeSCR(navController: NavController, homebaseViewModel: baseViewModel) {
	var isSearching by remember { mutableStateOf(false) }
	var searchText by remember { mutableStateOf("") }
	var showMenu by remember { mutableStateOf(false) }
	
	// Chat list placeholder (replace with your ViewModel data)
	val chatListState by remember { mutableStateOf(listOf<ChatListModel>()) }
	
	val currentUserPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""
	
	// Load chats (you can enable your ViewModel logic here)
	LaunchedEffect(currentUserPhone) {
		if (currentUserPhone.isNotEmpty()) {
			// homebaseViewModel.loadChatListRealtime(currentUserPhone)
		}
	}
	
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = { navController.navigate("newChat") },
				containerColor = colorResource(R.color.light_Green),
				contentColor = Color.White,
				modifier = Modifier.size(65.dp)
			) {
				Icon(
					painter = painterResource(R.drawable.add_24),
					contentDescription = "Add Chat",
					modifier = Modifier.size(28.dp)
				)
			}
		},
		bottomBar = { buttomNevigation() }
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.background(Color.White)
				.fillMaxSize()
		) {
			Spacer(modifier = Modifier.height(8.dp))
			
			// 🔹 Header (Search + Title)
			Box(modifier = Modifier.fillMaxWidth()) {
				if (isSearching) {
					OutlinedTextField(
						value = searchText,
						onValueChange = { searchText = it },
						placeholder = { Text("Search") },
						singleLine = true,
						modifier = Modifier
							.align(Alignment.CenterStart)
							.padding(start = 12.dp)
							.fillMaxWidth(0.8f),
						
						)
					IconButton(
						onClick = { isSearching = false; searchText = "" },
						modifier = Modifier.align(Alignment.CenterEnd)
					) {
						Icon(
							painter = painterResource(R.drawable.cancel_24),
							contentDescription = "Cancel"
						)
					}
				} else {
					Text(
						text = "Whatsapp",
						fontSize = 28.sp,
						color = colorResource(R.color.light_Green),
						modifier = Modifier
							.align(Alignment.CenterStart)
							.padding(start = 12.dp),
						fontWeight = FontWeight.Bold
					)
					Row(modifier = Modifier.align(Alignment.CenterEnd)) {
						IconButton(onClick = { /* camera */ }) {
							Icon(
								painter = painterResource(R.drawable.outline_photo_camera_24),
								contentDescription = null
							)
						}
						IconButton(onClick = { isSearching = true; searchText = "" }) {
							Icon(
								painter = painterResource(R.drawable.search_24),
								contentDescription = "Search"
							)
						}
					}
					IconButton(onClick = { showMenu = !showMenu }) {
						Icon(
							painter = painterResource(R.drawable.menu_24),
							contentDescription = "menu",
							modifier = Modifier.size(24.dp)
						)
						DropdownMenu(
							expanded = showMenu,
							onDismissRequest = { showMenu = false },
							modifier = Modifier.background(color = Color.White)
						) {
							DropdownMenuItem(
								text = { Text("New Group") },
								onClick = { showMenu = false })
							DropdownMenuItem(
								text = { Text("New Broadcast") },
								onClick = { showMenu = false })
							DropdownMenuItem(text = { Text("Linked Devices") }, onClick = {})
							DropdownMenuItem(
								text = { Text("Starred Messages") },
								onClick = { showMenu = false })
							DropdownMenuItem(
								text = { Text("Payments") },
								onClick = { showMenu = false })
							DropdownMenuItem(
								text = { Text("Settings") },
								onClick = { showMenu = false })
							navController.navigate(Routes.SettingSCR)
						}
					}
				}
			}
			
			Spacer(modifier = Modifier.height(8.dp))
			
			// 🔹 Chat list
			val filtered = if (searchText.isBlank()) chatListState else chatListState.filter {
				it.name?.contains(searchText, ignoreCase = true) == true ||
						it.lastMessage?.contains(searchText, ignoreCase = true) == true
			}
			
			Spacer(modifier = Modifier.height(8.dp))
			HorizontalDivider()
			Spacer(modifier = Modifier.height(12.dp))
			LazyColumn {
				items(chatData) { chat ->
					ChatItem(chat) }
				}
			}
		}
	}
	
	
	
	@Composable
	fun ChatItem(chat : ChatListModel, onClick : () -> Unit, avatarSize : Dp = 52.dp) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable(onClick = onClick)
				.padding(horizontal = 12.dp, vertical = 10.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Image(
				painter = painterResource(id = R.drawable.android_profile),
				contentDescription = "avatar",
				modifier = Modifier
					.size(avatarSize)
					.clip(CircleShape),
				contentScale = ContentScale.Crop
			)
			
			Column(
				modifier = Modifier
					.padding(start = 12.dp)
					.weight(1f)
			) {
				Text(
					text = chat.name ?: "Unknown",
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = chat.lastMessage ?: "",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = Color.Gray
				)
			}
			
			Text(
				text = chat.time ?: "--:--",
				color = Color.Gray,
				modifier = Modifier.padding(start = 8.dp)
			)
		}
	}
}

@Composable
fun ChatItem(chat : Int, onClick : () -> Unit) {
	TODO("Not yet implemented")
}

