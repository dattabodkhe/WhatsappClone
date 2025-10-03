package com.dukeey.whatsapp2.UpdateSCR

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.R

@Composable
@Preview(showSystemUi = true)
fun topBar() {
	var isSearching by remember {
		mutableStateOf(false)
	}
	var Showmenu by remember {
		mutableStateOf(false)
	}
	var Search by remember {
		mutableStateOf("")
	}
	
	Box(modifier = Modifier.fillMaxWidth()) {
		Column {
			Row {
				if (isSearching){
					TextField(value =Search, onValueChange = { Search = it}, placeholder = {
						Text(text = "Search")
					}, colors = TextFieldDefaults.colors(
						unfocusedContainerColor = Color.Transparent,
						focusedContainerColor =Color.Transparent,
						unfocusedIndicatorColor = Color.Transparent,
						focusedIndicatorColor = Color.Transparent),
						modifier = Modifier.padding(start = 12.dp), singleLine = true
						)
				}else{
					Text(text = "Updates", fontSize = 34.sp,
						fontWeight = FontWeight.Bold,
						modifier = Modifier.padding(start = 12.dp , top = 12.dp))
				}
				Spacer(modifier = Modifier.weight(1f))
				if (isSearching){
					IconButton(onClick = {isSearching=false}) {
						Icon(painter = painterResource(R.drawable.cancel_24),
							contentDescription = "icon", modifier = Modifier.size(23.dp))
					}
				}else{
					IconButton(onClick = {}) {
						Icon(painter = painterResource(R.drawable.outline_photo_camera_24),
							contentDescription = "camera",
							modifier = Modifier.size(28.dp))
					}
					IconButton(onClick = {isSearching = true}) {
						Icon(painter = painterResource(R.drawable.search_24),
							contentDescription = "camera",
							modifier = Modifier.size(28.dp))
					}
					IconButton(onClick = {Showmenu=true}) {
						Icon(painter = painterResource(R.drawable.menu_24),
							contentDescription = "camera",
							modifier = Modifier.size(28.dp))
						DropdownMenu(expanded = Showmenu, onDismissRequest = {Showmenu = false}) {
							DropdownMenuItem(text = {Text(text = "Status settings")}, onClick = {Showmenu=false})
							DropdownMenuItem(text = {Text(text = "Create Channel")}, onClick = {Showmenu=false})
							DropdownMenuItem(text = {Text(text = "Settings")}, onClick = {Showmenu=false})
							
							
							
							
							
						}
					}
				}
			}
			HorizontalDivider()
		}
	}
}