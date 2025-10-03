@file:Suppress("DEPRECATION")
package com.dukeey.whatsapp2.ViewModel

import PhoneAuthUser
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
	private val firebaseAuth: FirebaseAuth,
	private val database: FirebaseDatabase
) : ViewModel() {
	
	private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
	val authState = _authState.asStateFlow()
	
	private val userRef = database.reference.child("user")
	
	// Send OTP
	fun sendVerificationCode(phoneNumber: String, activity: Activity) {
		_authState.value = AuthState.Loading
		
		val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
				super.onCodeSent(id, token)
				Log.d("PhoneAuth", "onCodeSent: $id")
				_authState.value = AuthState.CodeSent(id)
			}
			
			override fun onVerificationCompleted(credential: PhoneAuthCredential) {
				signInWithCredential(credential, activity)
			}
			
			override fun onVerificationFailed(exception: FirebaseException) {
				Log.e("PhoneAuth", "Verification failed: ${exception.message}")
				_authState.value = AuthState.Error(exception.message ?: "Verification failed")
			}
		}
		
		val options = PhoneAuthOptions.newBuilder(firebaseAuth)
			.setPhoneNumber(phoneNumber)
			.setTimeout(60L, TimeUnit.SECONDS)
			.setActivity(activity)
			.setCallbacks(callbacks)
			.build()
		
		PhoneAuthProvider.verifyPhoneNumber(options)
	}
	
	// Verify OTP
	fun verifyCode(otp: String, context: Context) {
		val currentAuthState = _authState.value
		if (currentAuthState !is AuthState.CodeSent || currentAuthState.verificationId.isEmpty()) {
			Log.e("PhoneAuth", "Attempt to verify OTP without valid verificationId")
			_authState.value = AuthState.Error("Invalid verification process")
			return
		}
		
		val credential =
			PhoneAuthProvider.getCredential(currentAuthState.verificationId, otp)
		signInWithCredential(credential, context)
	}
	
	// Sign in with OTP credential
	private fun signInWithCredential(credential: PhoneAuthCredential, context: Context) {
		_authState.value = AuthState.Loading
		
		firebaseAuth.signInWithCredential(credential)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					val user = firebaseAuth.currentUser
					val phoneAuthUser = PhoneAuthUser(
						userId = user?.uid ?: "",
						phoneNumber = user?.phoneNumber ?: "",
						name = "",
						status = "",
						profileImage =""
					)
					
					markUserAsSignedIn(context)
					_authState.value = AuthState.Success(phoneAuthUser)
					
					// Save profile to DB
					userRef.child(phoneAuthUser.userId).setValue(phoneAuthUser)
					
					// Fetch profile
					fetchUserProfile(phoneAuthUser.userId)
				} else {
					_authState.value =
						AuthState.Error(task.exception?.message ?: "Sign-in failed")
				}
			}
	}
	
	// Save profile data with image
	fun saveUserProfile(userId: String, name: String, status: String, profile: Bitmap) {
		val encodedImage = convertBitmapToBase64(profile)
		val userProfile = PhoneAuthUser(
			userId = userId,
			phoneNumber = firebaseAuth.currentUser?.phoneNumber ?: "",
			name = name,
			status = status,
			profileImage = encodedImage)
		database.reference.child("user").child(userId).setValue(userProfile)
	}
	
	private fun fetchUserProfile(userId: String) {
		userRef.child(userId).get()
			.addOnSuccessListener { snapshot ->
				if (snapshot.exists()) {
					val userProfile = snapshot.getValue(PhoneAuthUser::class.java)
					if (userProfile != null) {
						_authState.value = AuthState.Success(userProfile)
					}
				}
			}
			.addOnFailureListener {
				_authState.value = AuthState.Error("Failed to fetch user profile")
			}
	}
	
	private fun markUserAsSignedIn(context: Context) {
		val sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
		sharedPreferences.edit().putBoolean("isSignedIn", true).apply()
	}
	
	fun resetAuthState() {
		_authState.value = AuthState.Ideal
	}
	
	fun signOut(context: Context) {
		firebaseAuth.signOut()
		val sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
		sharedPreferences.edit().putBoolean("isSignedIn", false).apply()
		_authState.value = AuthState.Ideal
	}
	
	// Convert Bitmap → Base64
	private fun convertBitmapToBase64(bitmap: Bitmap): String {
		val byteArrayOutputStream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
		val byteArray = byteArrayOutputStream.toByteArray()
		return Base64.encodeToString(byteArray, Base64.DEFAULT)
	}
}


// ---- AUTH STATE ----
sealed class AuthState {
	object Ideal : AuthState()
	object Loading : AuthState()
	data class CodeSent(val verificationId : String) : AuthState()
	data class Success(val user : PhoneAuthUser) : AuthState()
	data class Error(val message : String) : AuthState()
}
data class phoneAuthUser(
	val userId : String,
	val phoneNumber : String,
	val name : String,
	val status : String,
	val profile : Bitmap
)
