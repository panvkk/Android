package com.example.grid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grid.data.DataSource
import com.example.grid.model.Topic
import com.example.grid.ui.theme.GridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GridTheme {
                GridApp()
            }
        }
    }
}

@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(topic.drawableResourceId),
                contentDescription = stringResource(topic.stringResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(68.dp)
            )
            Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = stringResource(topic.stringResourceId),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row {
                    Icon(
                        painter = painterResource(R.drawable.workspaces),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${topic.amount}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopicsGrid(topicsList: List<Topic>, modifier: Modifier = Modifier) {
    LazyVerticalGrid (
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(topicsList) { topic ->
            Box {
                TopicCard(
                    topic = topic,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun GridApp(modifier: Modifier = Modifier) {
    Surface (
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        TopicsGrid(topicsList = DataSource.topics)
    }
}

@Preview
@Composable
fun TopicPreview(modifier: Modifier = Modifier) {
    Surface {
        GridApp()
    }
}

