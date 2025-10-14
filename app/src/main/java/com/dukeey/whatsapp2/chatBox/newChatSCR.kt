package com.dukeey.whatsapp2.chatBox

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase

@Composable
fun newChatSCR(navController: NavController) {
	var phoneNumber by remember { mutableStateOf("") }
	var name by remember { mutableStateOf("") }
	var isLoading by remember { mutableStateOf(false) }
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(20.dp),
		verticalArrangement = Arrangement.Center
	) {
		Text(
			text = "Start a New Chat",
			style = MaterialTheme.typography.titleLarge
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		OutlinedTextField(
			value = name,
			onValueChange = { name = it },
			label = { Text("Contact Name") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)
		
		Spacer(modifier = Modifier.height(12.dp))
		
		OutlinedTextField(
			value = phoneNumber,
			onValueChange = { phoneNumber = it },
			label = { Text("Phone Number") },
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
			modifier = Modifier.fillMaxWidth()
		)
		
		Spacer(modifier = Modifier.height(20.dp))
		
		Button(
			onClick = {
				if (phoneNumber.isNotBlank()) {
					isLoading = true
					val chatRef = FirebaseDatabase.getInstance().getReference("chats")
					val chatId = chatRef.push().key
					
					if (chatId != null) {
						val chatData = mapOf(
							"chatId" to chatId,
							"userId" to phoneNumber,
							"name" to name,
							"lastMessage" to "",
							"time" to ""
						)
						
						chatRef.child(chatId).setValue(chatData).addOnCompleteListener {
							isLoading = false
							navController.navigate("chatScreen/$phoneNumber")
						}
					}
				}
			},
			modifier = Modifier.fillMaxWidth()
		) {
			if (isLoading)
				CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
			else
				Text("Start Chat")
		}
	}
}
