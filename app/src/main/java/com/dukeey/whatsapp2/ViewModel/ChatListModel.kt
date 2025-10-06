package com.dukeey.whatsapp2.ViewModel

// Simple data model for a chat item
data class ChatListModel(
	val userId: String = "",
	val name: String = "",
	val phoneNumber: String = "",
	val lastMessage: String = "",
	val timestamp: Long = 0L,
	val profileImage: String = ""
)
