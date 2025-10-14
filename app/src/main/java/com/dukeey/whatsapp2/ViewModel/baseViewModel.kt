package com.dukeey.whatsapp2.ViewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.dukeey.whatsapp2.Model.Message
import com.dukeey.whatsapp2.homeSCR.ChatListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class baseViewModel(navController : NavHostController) : ViewModel() {
	
	// ✅ Live chat list for Jetpack Compose
	private val _chatlist = mutableStateOf<List<ChatListModel>>(emptyList())
	val chatlist: State<List<ChatListModel>> get() = _chatlist
	
	private val databaseReference = FirebaseDatabase.getInstance().reference
	
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
	
	// 🧩 Get chat list for a user (non-realtime)
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
	
	// 💬 Send a message (both sender & receiver)
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
	
	// 📩 Real-time message updates
	fun getMessages(
		senderPhoneNumber: String,
		receiverPhoneNumber: String,
		onNewMessage: (Message) -> Unit
	) {
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
	
	// 🕒 Get last message + timestamp for each chat
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
						val lastMessage =
							snapshot.children.firstOrNull()?.child("message")?.value as? String
						val timestamp =
							snapshot.children.firstOrNull()?.child("timeStamp")?.value?.toString()
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
	
	// 📲 One-time chat list fetch
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
									lastMessage = lastMessage,
									time = time,
									phoneNumber = phoneNumber,
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
	
	// ✅ Real-time chat list (auto-updates in UI)
	fun loadChatListRealtime(currentUserPhoneNumber: String) {
		val chatRef = FirebaseDatabase.getInstance().reference
			.child("chats")
			.child(currentUserPhoneNumber)
		
		chatRef.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				val chatList = mutableListOf<ChatListModel>()
				snapshot.children.forEach { child ->
					val chat = child.getValue(ChatListModel::class.java)
					chat?.let { chatList.add(it) }
				}
				_chatlist.value = chatList
			}
			
			override fun onCancelled(error: DatabaseError) {
				Log.e("BaseViewModel", "Chat list load cancelled: ${error.message}")
			}
		})
	}
	
	// 🧠 Decode base64 to bitmap (for profile pics)
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
