package com.example.mybusinesspage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybusinesspage.ui.theme.MyBusinessPageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBusinessPageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessPage()
                }
            }
        }
    }
}

@Composable
fun TextAndIcon(icon: Painter, description: String) {
    Row {
        Icon(
            painter = icon,
            contentDescription = description
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = description,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BusinessContacts(email: String, phone: String, tg: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TextAndIcon(
            icon = painterResource(R.drawable.email),
            description = email
        )
        TextAndIcon(
            icon = painterResource(R.drawable.call),
            description = phone
        )
        TextAndIcon(
            icon = painterResource(R.drawable.link),
            description = tg
        )
        Spacer(modifier = modifier.height(25.dp))
    }
}

@Composable
fun TitleImage(name: String, skill: String, modifier: Modifier = Modifier){
    val image = painterResource(R.drawable.android_logo)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier

    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = modifier
                .background(Color(0xFF18293B))
                .size(150.dp)
        )
        Text(
            text = name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = skill,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF0C7039)
        )
    }
}

@Composable
fun BusinessPage(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF94C49F))
    ) {
        Text(
            text = ""
        )
        TitleImage(
            name = stringResource(R.string.my_full_name),
            skill = stringResource(R.string.qualification),
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        BusinessContacts(
            email = stringResource(R.string.email_address),
            phone = stringResource(R.string.phone_number),
            tg = stringResource(R.string.telegram_link),
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBusinessPagePreview() {
    MyBusinessPageTheme {
        BusinessPage()
    }
}