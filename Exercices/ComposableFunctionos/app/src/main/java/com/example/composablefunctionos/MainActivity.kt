package com.example.composablefunctionos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composablefunctionos.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composablefunctionos.ui.theme.ComposableFunctionosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableFunctionosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposableFunctionsPage()
                }
            }
        }
    }
}


@Composable
fun RowInfo(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Text(
            text = "Row Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.row_description),
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
    }
}
@Composable
fun ColumnInfo(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Text(
            text = "Column Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.column_description),
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
    }
}
@Composable
fun ImageInfo(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Text(
            text = "Image Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.image_description),
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
    }
}
@Composable
fun TextInfo(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Text(
            text = "Text Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.row_description),
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
fun ComposableFunctionsPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier) {
            TextInfo(modifier)
            ImageInfo(modifier)
        }
        Row(modifier = Modifier) {
            RowInfo(modifier)
            ColumnInfo(modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposableFunctionosPriview() {
    ComposableFunctionosTheme {
        ComposableFunctionsPage(modifier = Modifier)
    }
}