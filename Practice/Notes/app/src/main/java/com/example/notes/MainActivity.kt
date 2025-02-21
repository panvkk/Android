package com.example.notes

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.notes.model.Note
import com.example.notes.model.getNotes
import com.example.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {

            }
        }
    }
}

@Composable
fun NotesApp(notes: List<Note>, modifier: Modifier = Modifier) {
    NotesTheme {
        Scaffold(
            topBar = {
                NotesTopBar()
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(notes) { item ->
                    NoteCard(
                        note = item,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_between_items))
                            .height(dimensionResource(R.dimen.card_height))
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.clickable {  }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .size(dimensionResource(R.dimen.icon_size)),
                imageVector = Icons.Filled.BookmarkBorder,
                contentDescription = null
            )
            Column {
                Text(
                    text = stringResource(note.titleRes),
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = stringResource(note.reminderTimeRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = null
            )
        }
    }
}

@Composable
fun NotesTopBar(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            text = stringResource(R.string.top_bar),
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Preview
@Composable
fun NoteAppPreview() {
    NotesApp(notes = getNotes())
}

