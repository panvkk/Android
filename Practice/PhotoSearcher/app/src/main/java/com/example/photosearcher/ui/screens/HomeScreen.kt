package com.example.photosearcher.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photosearcher.R
import com.example.photosearcher.model.SearchResult

@Composable
fun HomeScreen(
    searcherUiState: SearcherUiState,
    searcherViewModel: SearcherViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (searcherUiState) {
        is SearcherUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is SearcherUiState.Success ->
            PhotosColumn(
                contentPadding = contentPadding,
                photos = searcherViewModel.convertResult(
                    searchResult = searcherUiState.photos
                ),
                modifier = modifier
            )

        else ->
            ErrorScreen(modifier)
    }
}

@Composable
fun PhotosColumn(
    contentPadding: PaddingValues,
    photos: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier.padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(photos) { photo ->
            PhotoCard(
                photo,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun EditQueryField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(12.dp),
        shape = RoundedCornerShape(32.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Enter your query") },
            singleLine = true
        )
    }
}

@Preview
@Composable
fun EditFieldPreview() {
    EditQueryField(value = "ajisdfgjd", onValueChange = {})
}

//@Composable
//fun ShowResults(
//    photos: List<String>
//) {
//    LazyColumn {
//        items(photos) {
//            Text(text = it)
//        }
//    }
//}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(text = stringResource(R.string.is_connection_error),)
    }
}

@Composable
fun PhotoCard(imageUrl: String, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.photo),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_connection_error),
            placeholder = painterResource(R.drawable.loading_img)
        )
    }
}