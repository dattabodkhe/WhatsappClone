package com.dukeey.whatsapp2.UpdateSCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.R

@Composable
@Preview(showSystemUi = true)


fun MYstatus(){
	Row(
		modifier = Modifier.fillMaxWidth().padding(12.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box {
			Image(
				painter = painterResource(id = R.drawable.bmw_4),
				contentDescription = null,
				modifier = Modifier.size(80.dp).clip(shape = CircleShape),
				contentScale = ContentScale.Crop
			)
			Icon(
				painter = painterResource(id = R.drawable.add_24),
				contentDescription = null,
				modifier = Modifier.size(26.dp).align(Alignment.BottomEnd).padding(2.dp)
					.background(
						color = colorResource(id = R.color.light_Green),
						shape = RoundedCornerShape(12.dp)
					), tint = Color.White
			
			)
		}
		
		Spacer(modifier = Modifier.width(12.dp))
		Column {
			Text(text = "MyStatus", fontWeight = FontWeight.Bold, fontSize = 23.sp)
			Text(text = "tap to add status update", color = Color.Gray, fontSize = 16.sp)
		}
		
	}
	
}
data class StatusData(val image:Int,val time: String,val name: String)


@Composable

fun Statusitem(
	statusData : StatusData
){
	
	Row (modifier = Modifier.fillMaxWidth().
	padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically){
		Image(painter = painterResource(id = statusData.image), contentDescription = null,
			modifier = Modifier.size(55.dp).padding(3.dp).clip(shape = CircleShape),
			contentScale = ContentScale.Crop
		
		)
		Spacer(modifier = Modifier.width(12.dp))
		Column {
			Text(text = statusData.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
			
			Text(text = statusData.time, fontSize = 14.sp, color = Color.Gray)
			
		}
		
	}
	
}
