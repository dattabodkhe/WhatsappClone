package com.dukeey.whatsapp2.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import coil.memory.MemoryCache
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.Callback
import kotlin.collections.emptyList

import kotlin.jvm.java


class Baseviewmodel : ViewModel(){
	fun searchUserbyPhoneNumber(phoneNumber : String, callback :(ChatListModel?)-> Unit) {
		val currentUser = FirebaseAuth.getInstance().currentUser
		if (currentUser != null) {
			Log.e("BaseViewModel", "User is not authenticated")
			callback(null)
			
		}
		val databaseReference = FirebaseDatabase.getInstance().getReference("user")
		databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
			.addValueEventListener(object : ValueEventListener {
				
				override fun onDataChange(snapshot : DataSnapshot){
					if (snapshot.exists()){
						val user= snapshot.children.first().getValue(ChatListModel::class.java)
						callback(user)
					}else{
						callback(null)
					}
				}
				override fun onCancelled(error: DatabaseError){
					Log.e("BaseViewModel","Error fetching user:${error.message}.Details${error.details}")
					callback(null)
				}
			}
			)
	}
	fun getChatforUser(userId: String,callback :(list<ChatListModel>)-> Unit){
		val chatRef= FirebaseDatabase.getInstance().getReference("user/$userId/chat")
		chatRef.orderByChild("userId").equalTo(userId)
			.addValueEventListener(object : ValueEventListener{
				override fun onDataChange(snapshot : DataSnapshot) {
					val chatList = mutableListOf<ChatListModel>()
					
					for (childSnapshot in snapshot.children){
						val chat = childSnapshot.getValue(ChatListModel::class.java)
						if (chat!=null){
							chatList.add(chat)
						}
					}
					callback(chatList)
				}
				override fun onCancelled(error: DatabaseError) {
					Log.e("BaseViewModel","Error fetching user chat:${error.message}")
					callback(emptyList())
				}
			})
		
	}
	private val _chatList = mutableListOf<List<ChatListModel>>(emptyList())
	val chatList= _chatList.asStateFlow()
	
	fun localChatData(){
		val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
		if (currentUserId!=null){
			val  chatRef = FirebaseDatabase.getInstance().getReference("chat")
			 chatRef.orderByChild("userId").equalTo(currentUserId)
				 .addValueEventListener(object : ValueEventListener{
					 override fun onDataChange(snapshot : DataSnapshot) {
						 val chatList = mutableListOf<ChatListModel>()
						 for (childSnapshot in snapshot.children){
							 val chat = childSnapshot.getValue(ChatListModel::class.java)
							 if (chat!=null){
								 chatList.add(chat)
							 }
						 }
					 }
				 })
		}
	}
	
}
