package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
            NotesApp(notes = getNotes())
        }
    }
}

@Composable
fun NotesApp(notes: List<Note>, modifier: Modifier = Modifier) {
    NotesTheme() {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
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
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if(expanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.secondaryContainer,
    )

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .animateContentSize (
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .size(dimensionResource(R.dimen.icon_size)),
                    imageVector = Icons.Filled.BookmarkBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Column {
                    Text(
                        text = stringResource(note.titleRes),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
                    imageVector = if (expanded) {
                        Icons.Filled.ExpandMore }
                    else {
                        Icons.Filled.ExpandLess
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            if(expanded) AdditionInfo(
                reminderTimeRes = note.reminderTimeRes,
                descriptionRes = note.descriptionRes,
                imageRes = note.imageRes
            )
        }
    }
}

@Composable
fun AdditionInfo(
    @StringRes reminderTimeRes: Int,
    @StringRes descriptionRes: Int,
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier
) {
    Column {
        Image(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .clip(MaterialTheme.shapes.medium),
            painter = painterResource(imageRes),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            text = stringResource(descriptionRes),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium),
                )
        )
        Text(
            text = stringResource(reminderTimeRes),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small),
                )
        )
    }
}

@Composable
fun NotesTopBar(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
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

