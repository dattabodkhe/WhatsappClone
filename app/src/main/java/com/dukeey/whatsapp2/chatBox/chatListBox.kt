package com.dukeey.whatsapp2.chatBox

import android.graphics.Bitmap
import com.dukeey.whatsapp2.homeSCR.ChatListModel
import com.dukeey.whatsapp2.R
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dukeey.whatsapp2.ViewModel.baseViewModel

@Composable
fun ChatDesign(chatlistModel : ChatListModel,onclick:()->Unit,
			   homebaseViewModel: baseViewModel
){
	
	Row (modifier = Modifier.padding(11.dp),
		verticalAlignment = Alignment.CenterVertically)
	{
		var profileImage = chatlistModel?.profileImage
		val bitmap = remember {
			profileImage?.let { baseViewModel.base64toBitmap(it) }
		}
		Image(
			painter = if (bitmap!=null){
				rememberImagePainter(bitmap)
			}else{
				painterResource(R.drawable.android_profile)
			},
			contentDescription = "Profile Image",
			modifier = Modifier
				.size(50.dp)
				.clip(CircleShape)
		
		)
		Spacer(modifier = Modifier.height(21.dp))
		
		Column( ) {
			Row(horizontalArrangement = Arrangement
				.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
				
				Text(text = chatlistModel.name?:"Unknown",
					fontWeight = FontWeight.Bold,
					fontSize = 23.sp)
				
				Text(text = chatlistModel.time?:"--:--", color = Color.Gray)
			}
			Spacer(modifier = Modifier.height(4.dp))
			Text(text=chatlistModel.message?:"messages", fontSize = 14.sp,
				color = Color.Gray, fontWeight = FontWeight.SemiBold)
		}
		
		
		
	}
}

data class ChatListModel(
	val name: String,
	val message: String,
	val time: String,)