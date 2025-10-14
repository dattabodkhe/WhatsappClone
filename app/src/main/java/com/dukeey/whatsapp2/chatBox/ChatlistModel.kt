package com.dukeey.whatsapp2.homeSCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dukeey.whatsapp2.ViewModel.baseViewModel

@Composable
fun ChatListItem(
	chat: ChatListModel,
	onClick: () -> Unit,
	homeViewModel: baseViewModel
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() }
			.padding(10.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		if (chat.image != null) {
			Image(
				bitmap = chat.image.asImageBitmap(),
				contentDescription = null,
				modifier = Modifier
					.size(55.dp)
					.clip(CircleShape)
			)
		} else {
			Icon(
				imageVector = Icons.Default.AccountCircle,
				contentDescription = null,
				modifier = Modifier.size(55.dp)
			)
		}
		
		Spacer(modifier = Modifier.width(12.dp))
		
		Column(modifier = Modifier.weight(1f)) {
			Text(chat.name, fontWeight = FontWeight.Bold)
			Text(chat.message, style = MaterialTheme.typography.bodySmall)
		}
		
		Text(
			text = chat.time,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier.padding(start = 6.dp)
		)
	}
}
