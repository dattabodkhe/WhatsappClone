package com.dukeey.whatsapp2.callSCR

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dukeey.whatsapp2.R

@Composable

fun FavSeaction (){
	
	val sampleFavourite=listOf(FavContact(image = R.drawable.krishna, name = "Krishna ji"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.obito, name = "obito"),
		FavContact(image = R.drawable.kakashi, name = "kakashi"),
		FavContact(image = R.drawable.bmw_4, name = "friend"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo"),
		FavContact(image = R.drawable.shinobo, name = "shinobo")
	
	)
	
	
	Column(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 19.dp)) {
		Text(text = "Favourite", fontSize = 20.sp
			, fontWeight = FontWeight.Bold
			, modifier = Modifier.padding(bottom = 8.dp)
		)
		
		Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
		sampleFavourite.forEach {
			FavouriteItem(it)
		}
		}
	}
}


data class  FavContact(
	val name:String,
	val image: Int

)
