package com.dukeey.whatsapp2.ViewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.dukeey.whatsapp2.Model.Message
import com.dukeey.whatsapp2.homeSCR.ChatListModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class baseViewModel : ViewModel() {
	
	// 🔍 Search user by phone number
	fun searchUserByPhoneNumber(phoneNumber: String, callback: (ChatListModel?) -> Unit) {
		val currentUser = FirebaseAuth.getInstance().currentUser
		if (currentUser == null) {
			Log.e("BaseViewModel", "User is not authenticated")
			callback(null)
			return
		}
		
		val databaseReference = FirebaseDatabase.getInstance().getReference("user")
		databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
			.addListenerForSingleValueEvent(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					if (snapshot.exists()) {
						val user = snapshot.children.first().getValue(ChatListModel::class.java)
						callback(user)
					} else {
						callback(null)
					}
				}
				
				override fun onCancelled(error: DatabaseError) {
					Log.e("BaseViewModel", "Error fetching user: ${error.message}")
					callback(null)
				}
			})
	}
	
	// 💬 Get chats for user
	fun getChatForUser(userId: String, callback: (List<ChatListModel>) -> Unit) {
		val chatRef = FirebaseDatabase.getInstance().getReference("user/$userId/chat")
		chatRef.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				val chatList = mutableListOf<ChatListModel>()
				for (childSnapshot in snapshot.children) {
					val chat = childSnapshot.getValue(ChatListModel::class.java)
					if (chat != null) {
						chatList.add(chat)
					}
				}
				callback(chatList)
			}
			
			override fun onCancelled(error: DatabaseError) {
				Log.e("BaseViewModel", "Error fetching user chat: ${error.message}")
				callback(emptyList())
			}
		})
	}
	
	private val databaseReference = FirebaseDatabase.getInstance().reference
	
	// 📩 Send message
	fun sendMessage(senderPhoneNumber: String, receiverPhoneNumber: String, messageText: String) {
		val messageId = databaseReference.push().key ?: return
		val message = Message(
			senderPhoneNumber = senderPhoneNumber,
			message = messageText,
			timeStamp = System.currentTimeMillis()
		)
		
		databaseReference.child("messages")
			.child(senderPhoneNumber)
			.child(receiverPhoneNumber)
			.child(messageId)
			.setValue(message)
		
		databaseReference.child("messages")
			.child(receiverPhoneNumber)
			.child(senderPhoneNumber)
			.child(messageId)
			.setValue(message)
	}
	
	// 📥 Get messages in real time
	fun getMessages(senderPhoneNumber: String, receiverPhoneNumber: String, onNewMessage: (Message) -> Unit) {
		val messageRef = databaseReference.child("messages")
			.child(senderPhoneNumber)
			.child(receiverPhoneNumber)
		
		messageRef.addChildEventListener(object : ChildEventListener {
			override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
				val message = snapshot.getValue(Message::class.java)
				if (message != null) {
					onNewMessage(message)
				}
			}
			
			override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
			override fun onChildRemoved(snapshot: DataSnapshot) {}
			override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
			override fun onCancelled(error: DatabaseError) {
				Log.e("BaseViewModel", "Error: ${error.message}")
			}
		})
	}
	
	// 🕓 Fetch last message for chat
	fun fetchLastMessageForChat(
		senderPhoneNumber: String,
		receiverPhoneNumber: String,
		onLastMessageFetched: (String, String) -> Unit
	) {
		val chatRef = FirebaseDatabase.getInstance().reference
			.child("messages")
			.child(senderPhoneNumber)
			.child(receiverPhoneNumber)
		
		chatRef.orderByChild("timeStamp").limitToLast(1)
			.addListenerForSingleValueEvent(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					if (snapshot.exists()) {
						val lastMessage = snapshot.children.firstOrNull()?.child("message")?.value as? String
						val timestamp = snapshot.children.firstOrNull()?.child("timeStamp")?.value?.toString()
						onLastMessageFetched(lastMessage ?: "No message", timestamp ?: "--:--")
					} else {
						onLastMessageFetched("No message", "--:--")
					}
				}
				
				override fun onCancelled(error: DatabaseError) {
					onLastMessageFetched("No message", "--:--")
				}
			})
	}
	
	// 📋 Load chat list
	fun loadChatList(currentUserPhoneNumber: String, onChatListLoaded: (List<ChatListModel>) -> Unit) {
		val chatList = mutableListOf<ChatListModel>()
		val chatRef = FirebaseDatabase.getInstance().reference
			.child("chats")
			.child(currentUserPhoneNumber)
		chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				if (snapshot.exists()) {
					snapshot.children.forEach { child ->
						val phoneNumber = child.key ?: return@forEach
						val name = child.child("name").value as? String ?: "Unknown"
						val image = child.child("image").value as? String
						val profileImageBitmap = image?.let { decodeBase64ToBitmap(it) }
						
						fetchLastMessageForChat(currentUserPhoneNumber, phoneNumber) { lastMessage, time ->
							chatList.add(
								ChatListModel(
									name = name,
									image = profileImageBitmap,
									message = lastMessage,
									time = time,
									userId = phoneNumber,
									timestamp = time,
									phoneNumber = phoneNumber,
									lastMessage = lastMessage,
									profileImage = image
								)
							)
							
							if (chatList.size == snapshot.childrenCount.toInt()) {
								onChatListLoaded(chatList)
							}
						}
					}
				} else {
					onChatListLoaded(emptyList())
				}
			}
			
			override fun onCancelled(error: DatabaseError) {
				onChatListLoaded(emptyList())
			}
		})
	}
	
	// 🧩 Convert Base64 → Bitmap
	private fun decodeBase64ToBitmap(base64Image: String): Bitmap? {
		return try {
			val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
			BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
		} catch (e: Exception) {
			Log.e("BaseViewModel", "Decode failed: ${e.message}")
			null
		}
	}
}
