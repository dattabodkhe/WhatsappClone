package com.dukeey.whatsapp2.homeSCR

import android.graphics.Bitmap

data class ChatListModel(
	val name: String = "",
	val image: Bitmap? = null,
	val message: String = "",
	val time: String = "",
	val userId: String = "",
	val timestamp: String = "",
	val phoneNumber: String = "",
	val lastMessage: String = "",
	val profileImage: String? = null
)
