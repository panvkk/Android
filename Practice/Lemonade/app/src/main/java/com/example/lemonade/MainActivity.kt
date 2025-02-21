package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun ButtonImage(
    title: String,
    image: Painter,
    name: String,
    onClickBehavior: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = name,
            modifier = modifier
                .size(256.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color = Color(0xFFa5e8b8))
                .border(width = 2.dp, color = Color(0xFFa5e8b8), shape = RoundedCornerShape(16.dp))
                .clickable(onClick = onClickBehavior)
        )
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = modifier
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun LemonApp() {
    var index by remember { mutableStateOf(1) }

    var squeezeCounter by remember { mutableStateOf(0) }

    val imageResource = when (index) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        4 -> R.drawable.lemon_restart
        else -> R.drawable.lemon_tree
    }
    val titleResource = when (index) {
        1 -> R.string.lemon_tree_title
        2 -> R.string.lemon_title
        3 -> R.string.glass_of_lemonade_title
        4 -> R.string.empty_glass_title
        else -> R.string.lemon_tree_title
    }
    val nameResource = when (index) {
        1 -> R.string.lemon_tree
        2 -> R.string.lemon
        3 -> R.string.glass_of_lemonade
        4 -> R.string.empty_glass
        else -> R.string.lemon_tree
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Yellow)
                    .height(50.dp)
            ) {
                Text(
                    text = "Lemonade",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .align(alignment = Alignment.BottomCenter)
                )
            }
            when(index) {
                1 -> ButtonImage(
                    title = stringResource(titleResource),
                    image = painterResource(imageResource),
                    name = stringResource(nameResource),
                    onClickBehavior = {
                        index++
                        squeezeCounter = (3..10).random()
                    }
                )
                2 -> ButtonImage(
                    title = stringResource(titleResource),
                    image = painterResource(imageResource),
                    name = stringResource(nameResource),
                    onClickBehavior = {
                        squeezeCounter--
                        if(squeezeCounter == 0) {
                            index++
                        }
                    }
                )
                3 -> ButtonImage(
                    title = stringResource(titleResource),
                    image = painterResource(imageResource),
                    name = stringResource(nameResource),
                    onClickBehavior = {
                        index++
                    }
                )
                4 -> ButtonImage(
                    title = stringResource(titleResource),
                    image = painterResource(imageResource),
                    name = stringResource(nameResource),
                    onClickBehavior = {
                        index = 1
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}