package com.dukeey.whatsapp2.chatBox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dukeey.whatsapp2.Model.Message
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.ViewModel.baseViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun chatScreen(
	navController: NavController,
	receiverPhone: String,
	homebaseViewModel: baseViewModel
) {
	val senderPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""
	val messages = remember {
		mutableStateListOf<Message>() }
	var messageText by remember { mutableStateOf("") }
	
	LaunchedEffect(receiverPhone) {
		homebaseViewModel.getMessages(senderPhone, receiverPhone) { newMessage ->
			messages.add(newMessage)
		}
	}
	
	Scaffold(
		bottomBar = {
			Row(
				Modifier
					.fillMaxWidth()
					.background(Color.White)
					.padding(8.dp)
			) {
				TextField(
					value = messageText,
					onValueChange = { messageText = it },
					placeholder = { Text("Type a message") },
					modifier = Modifier.weight(1f)
				)
				IconButton(onClick = {
					if (messageText.isNotBlank()) {
						homebaseViewModel.sendMessage(senderPhone, receiverPhone, messageText)
						messageText = ""
					}
				}) {
					Icon(painter = painterResource(R.drawable.arrow1), contentDescription = "Send")
				}
			}
		}
	) { padding ->
		LazyColumn(
			modifier = Modifier
				.padding(padding)
				.background(Color(0xFFEFEFEF))
				.fillMaxSize()
		) {
			items(messages) { msg ->
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp),
					contentAlignment = if (msg.senderPhoneNumber == senderPhone) Alignment.CenterEnd else Alignment.CenterStart
				) {
					Text(
						text = msg.message,
						modifier = Modifier
							.background(
								if (msg.senderPhoneNumber == senderPhone) Color(0xFFDCF8C6)
								else Color.White
							)
							.padding(12.dp)
					)
				}
			}
		}
	}
}
