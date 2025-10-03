package com.dukeey.whatsapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dukeey.whatsapp2.Nevigation.WhatsAppNevSystem
import com.dukeey.whatsapp2.ui.theme.Whatsapp2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			Whatsapp2Theme {
				WhatsAppNevSystem()
			}
		}
	}
}
