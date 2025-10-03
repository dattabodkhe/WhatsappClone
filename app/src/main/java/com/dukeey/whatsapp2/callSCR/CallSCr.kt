package com.dukeey.whatsapp2.callSCR

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.R


@Composable
fun CallitemDesign(call : Call){
	Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
		
		Image(painter = painterResource(call.image),
			contentDescription = null, modifier = Modifier.size(60.dp).
			clip(shape = CircleShape), contentScale = ContentScale.Crop)
		
		
		Spacer(modifier = Modifier.width(12.dp))
		Column {
			Text(text =call.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
			
			Row {
				Icon(painter = painterResource(R.drawable.mised_call_2),
					contentDescription = "Icon",
					modifier = Modifier.size(18.dp),
					tint = if (call.isMissed) Color.Red else colorResource(R.color.light_Green))
				
				Spacer(modifier = Modifier.width(4.dp))
				Text(text = call.time, color = Color.Gray,
					fontWeight = FontWeight.SemiBold,
					fontSize = 16.sp)
			}
		}
	}
	
}
data class Call(
	val name: String,
	val image: Int,
	val time: String,
	val isMissed: Boolean
)
