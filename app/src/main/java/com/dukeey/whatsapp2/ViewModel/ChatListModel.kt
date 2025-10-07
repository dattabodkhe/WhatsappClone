package com.dukeey.whatsapp2.homeSCR

import android.graphics.Bitmap

data class ChatListModel(
	val userId : String = "",
	val name : String = "",
	val phoneNumber : String = "",
	val message : String = "",
	val time : String = "",
	val timestamp : Long = 0L,
	val image : Bitmap? = null,
	val profileImage : String? = null,
	val lastMessage : String = ""
)
