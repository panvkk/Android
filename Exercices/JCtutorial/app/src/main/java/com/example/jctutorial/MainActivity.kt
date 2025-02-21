package com.example.jctutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jctutorial.ui.theme.JCTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JCTutorialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TutorialPage()
                }
            }
        }
    }
}

@Composable
fun TutorialText(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ){
        Text(
            text = stringResource(R.string.tutorial_name),
            fontSize = 24.sp,
            lineHeight = 12.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = stringResource(R.string.jetpack_compose_attribute),
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = stringResource(R.string.tutorial_description),
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun TutorialImage(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.bg_compose_background)
    Image(
        painter = image,
        contentDescription = null
    )
}

@Composable
fun TutorialPage(modifier: Modifier = Modifier) {
    Column(modifier) {
        TutorialImage()
        TutorialText()
    }
}

@Preview(showBackground = true)
@Composable
fun TutorualPriview() {
    JCTutorialTheme {
        TutorialPage()
    }
}