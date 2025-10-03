
package com.dukeey.whatsapp2.loginSCr

import Routes
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.dukeey.whatsapp2.R
import com.dukeey.whatsapp2.ViewModel.AuthState
import com.dukeey.whatsapp2.ViewModel.PhoneAuthViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun UserloginSCR(
	navController: NavController,
	phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {
	val authState by phoneAuthViewModel.authState.collectAsState()
	val context = LocalContext.current
	val activity = LocalContext.current as Activity
	
	var otp by remember { mutableStateOf("") }
	var verificationId by remember { mutableStateOf<String?>(null) }
	
	var expanded by remember { mutableStateOf(false) }
	var countryCode by remember { mutableStateOf("") }
	var phoneNumber by remember { mutableStateOf("") }
	var seleteCountry by remember { mutableStateOf("India") }
	
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(color = Color.White)
			.padding(18.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = "Enter your phone Number",
			fontWeight = FontWeight.Bold,
			fontSize = 23.sp,
			color = colorResource(R.color.light_Green)
		)
		Spacer(modifier = Modifier.height(9.dp))
		Column {
			Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
				Text(text = "Whatsapps needs your numbers", fontSize = 16.sp)
				Text(
					text = "Whats",
					color = colorResource(R.color.light_Green),
					fontWeight = FontWeight.Bold
				)
			}
			Text(
				text = "your number",
				modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
				color = colorResource(R.color.light_Green),
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp
			)
			
			TextButton(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
				Box(modifier = Modifier.width(77.dp)) {
					Text(
						text = seleteCountry,
						modifier = Modifier.align(Alignment.Center),
						fontSize = 16.sp
					)
					Spacer(modifier = Modifier.width(8.dp))
					
					Icon(
						imageVector = Icons.Default.ArrowDropDown,
						contentDescription = "Defaut icon",
						modifier = Modifier.align(Alignment.CenterEnd)
					)
					DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
						listOf("India", "UA", "UK", "Japan", "China", "Europe").forEach { country ->
							DropdownMenuItem(
								text = { Text(text = country) },
								onClick = {
									seleteCountry = country
									expanded = false
								}
							)
						}
					}
				}
			}
			HorizontalDivider(
				modifier = Modifier.padding(horizontal = 66.dp),
				thickness = 5.dp,
				color = colorResource(R.color.light_Green)
			)
		}
		
		Spacer(modifier = Modifier.height(2.dp))
		
		when (authState) {
			is AuthState.Ideal, is AuthState.Loading, is AuthState.CodeSent -> {
				if (authState is AuthState.CodeSent) {
					verificationId = (authState as AuthState.CodeSent).verificationId
				}
				
				if (verificationId == null) {
					Spacer(modifier = Modifier.height(16.dp))
					Row(verticalAlignment = Alignment.CenterVertically) {
						TextField(
							value = countryCode,
							onValueChange = { countryCode = it },
							modifier = Modifier.width(70.dp),
							singleLine = true,
							colors = TextFieldDefaults.colors(
								unfocusedIndicatorColor = colorResource(R.color.light_Green),
								focusedIndicatorColor = colorResource(R.color.light_Green),
								unfocusedContainerColor = Color.Transparent,
								focusedContainerColor = Color.Transparent
							)
						)
						Spacer(modifier = Modifier.width(9.dp))
						TextField(
							value = phoneNumber,
							onValueChange = { phoneNumber = it },
							placeholder = { Text(text = "PhoneNumber") },
							singleLine = true,
							colors = TextFieldDefaults.colors(
								focusedContainerColor = Color.Transparent,
								unfocusedContainerColor = Color.Transparent,
								focusedIndicatorColor = Color.Transparent
							)
						)
					}
					Spacer(modifier = Modifier.height(16.dp))
					
					Button(
						onClick = {
							if (phoneNumber.isNotEmpty()) {
								val fullphoneNumber = "$countryCode$phoneNumber"
								phoneAuthViewModel.sendVerificationCode(fullphoneNumber, activity)
							} else {
								Toast.makeText(
									context,
									"Enter your valid number",
									Toast.LENGTH_LONG
								).show()
							}
						},
						shape = RoundedCornerShape(6.dp),
						colors = ButtonDefaults.buttonColors(colorResource(R.color.teal_700))
					) {
						Text(text = "Enter OTP")
					}
					
					if (authState is AuthState.Loading) {
						Spacer(modifier = Modifier.height(16.dp))
						CircularProgressIndicator()
					}
					
				} else {
					// OTP input screen
					Spacer(modifier = Modifier.height(40.dp))
					Text(
						text = "Enter Otp",
						fontWeight = FontWeight.Bold,
						color = colorResource(R.color.teal_700)
					)
					Spacer(modifier = Modifier.height(8.dp))
					
					TextField(
						value = otp,
						onValueChange = { otp = it },
						placeholder = { Text(text = "Otp") },
						modifier = Modifier.fillMaxWidth(),
						singleLine = true,
						colors = TextFieldDefaults.colors(
							focusedContainerColor = Color.Transparent,
							unfocusedContainerColor = Color.Transparent,
							focusedIndicatorColor = Color.Transparent
						)
					)
					Spacer(modifier = Modifier.height(32.dp))
					Button(
						onClick = {
							if (otp.isNotEmpty() && verificationId != null) {
								phoneAuthViewModel.verifyCode(otp, context)
							} else {
								Toast.makeText(
									context,
									"please enter valid otp",
									Toast.LENGTH_LONG
								).show()
							}
						},
						shape = RoundedCornerShape(6.dp),
						colors = ButtonDefaults.buttonColors(colorResource(R.color.teal_700))
					) {
						Text(text = "Verify Otp")
					}
					
					if (authState is AuthState.Loading) {
						Spacer(modifier = Modifier.height(16.dp))
						CircularProgressIndicator()
					}
				}
			}
			
			is AuthState.Success -> {
				Log.d("PhoneAuth", "Login Successful")
				
				navController.navigate(Routes.UserProfileSetSCR) {
					popUpTo(Routes.UserloginSCR) { inclusive = true }
					launchSingleTop = true
				}
				
				phoneAuthViewModel.resetAuthState()
			}
			
			
			is AuthState.Error -> {
				Toast.makeText(
					context,
					(authState as AuthState.Error).message,
					Toast.LENGTH_LONG
				).show()
			}
		}
	}
}
