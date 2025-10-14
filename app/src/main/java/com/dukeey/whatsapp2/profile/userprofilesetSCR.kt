@file:Suppress("DEPRECATION")

package com.dukeey.whatsapp2.profile

import Routes
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.ViewModel.PhoneAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun UserProfileSetSCR(
	phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel(),
	navController: NavController
) {
	
	var name by remember { mutableStateOf("") }
	var status by remember { mutableStateOf("") }
	var profileImageUrl by remember { mutableStateOf<Uri?>(null) }
	var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }
	
	val firebaseAuth = Firebase.auth
	val phoneNumber = firebaseAuth.currentUser?.phoneNumber ?: ""
	val userId = firebaseAuth.currentUser?.uid ?: ""
	val context = LocalContext.current
	
	val imagePickerLauncher = rememberLauncherForActivityResult(
		ActivityResultContracts.GetContent()
	) { uri: Uri? ->
		profileImageUrl = uri
		uri?.let {
			bitmapImage = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
				android.provider.MediaStore.Images.Media.getBitmap(
					context.contentResolver,
					it
				)
			} else {
				val source = ImageDecoder.createSource(context.contentResolver, it)
				ImageDecoder.decodeBitmap(source)
			}
		}
	}
	
	Column(
		modifier = Modifier.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			modifier = Modifier
				.size(120.dp)
				.clip(CircleShape)
				.border(2.dp, Color.Gray, CircleShape)
				.clickable { imagePickerLauncher.launch("image/*") }
		) {
			when {
				bitmapImage != null -> {
					Image(
						bitmap = bitmapImage!!.asImageBitmap(),
						contentDescription = null,
						modifier = Modifier.fillMaxSize().clip(CircleShape),
						contentScale = ContentScale.Crop
					)
				}
				
				profileImageUrl != null -> {
					AsyncImage(
						model = profileImageUrl,
						contentDescription = null,
						modifier = Modifier.fillMaxSize().clip(CircleShape),
						contentScale = ContentScale.Crop
					)
				}
				
				else -> {
					Image(
						painter = painterResource(R.drawable.android_profile),
						contentDescription = null,
						modifier = Modifier.fillMaxSize().align(Alignment.Center)
					)
				}
			}
		}
		
		Spacer(modifier = Modifier.height(16.dp))
		Text(text = phoneNumber)
		
		Spacer(modifier = Modifier.height(16.dp))
		TextField(
			value = name,
			onValueChange = { name = it },
			label = { Text(text = "Name") },
			modifier = Modifier.fillMaxWidth(),
			colors = TextFieldDefaults.colors(
				unfocusedContainerColor = Color.Transparent,
				focusedContainerColor = Color.Transparent,
				unfocusedIndicatorColor = colorResource(R.color.light_Green)
			)
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		TextField(
			value = status,
			onValueChange = { status = it },
			label = { Text(text = "Status") },
			modifier = Modifier.fillMaxWidth(),
			colors = TextFieldDefaults.colors(
				unfocusedContainerColor = Color.Transparent,
				focusedContainerColor = Color.Transparent,
				unfocusedIndicatorColor = colorResource(R.color.light_Green)
			)
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		Button(
			onClick = {
				val currentUser = FirebaseAuth.getInstance().currentUser
				val uid = currentUser?.uid
				
				if (uid == null) {
					Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show()
					return@Button
				}
				
				if (bitmapImage == null) {
					Toast.makeText(context, "Please select a profile image", Toast.LENGTH_SHORT)
						.show()
					return@Button
				}
				
				if (name.isBlank()) {
					Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
					return@Button
				}
				
				phoneAuthViewModel.saveUserProfile(uid, name.trim(), status.trim(), bitmapImage!!)
				navController.navigate(Routes.HomeSCR)
			
		
	},
			colors = ButtonDefaults.buttonColors(colorResource(R.color.light_Green))
		) {
			Text(text = "Save")
		}
	}
}
