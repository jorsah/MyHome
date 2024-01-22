package com.example.myhome.ui.uikit

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ItemCard(
    name: String,
    isFavorite: Boolean,
    imageUrl: String,
) {

    Box {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(30.dp))

        ) {
            //I get empty images here but code is working well with other urls
            AsyncImage(
//            model = "https://upload.wikimedia.org/wikipedia/commons/d/d6/Uniform_Resource_Locator_%28URL%29_example.PNG",
                model = imageUrl,
                placeholder = painterResource(id = android.R.drawable.ic_menu_camera),
                error = painterResource(id = android.R.drawable.stat_notify_error),
                contentDescription = "snapshot",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )

            Text(modifier = Modifier.fillMaxWidth(), text = name, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(4.dp))
        }

        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(24.dp),
            painter = painterResource(
                id = R.drawable.btn_star_big_on
            ), contentDescription = "favorite",
            tint = if (isFavorite) Color(0xFFBBB4B4)
            else Color(0xFFFFE54A)
        )
    }
}