package com.example.composables

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.composables.ui.theme.ComposablesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposablesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposablesPage()
                }
            }
        }
    }
}

@Composable
fun RowInfo(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Row Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.row_description),
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun TextInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Text Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.text_description),
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun ImageInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Image Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.image_description),
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun ColumnInfo(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Column Composable",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.column_description),
            modifier = modifier
        )
    }
}

@Composable
fun ComposablesPage(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Box(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFEADDFF))
            ) {
                TextInfo()
            }
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color(0xFFD0BCFF))
            ) {
                ImageInfo()
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Box(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFB69DF8))

            ) {
                RowInfo()
            }
            Box(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFF6EDFF))
            ) {
                ColumnInfo()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposablesPreview() {
    ComposablesTheme {
        ComposablesPage()
    }
}