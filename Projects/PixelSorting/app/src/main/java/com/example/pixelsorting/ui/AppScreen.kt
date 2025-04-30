package com.example.pixelsorting.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixelSorterScreen(
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory)
) {
    val originalUri = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/5/5e/Flag_of_Ukraine.jpg")
    var effectLevel by remember { mutableIntStateOf(50)}
    var sortType by remember { mutableStateOf(SortType.COLUMNS) }
    var sortKey by remember { mutableStateOf(SortKey.BRIGHTNESS) }

    var showSortTypeDialog by remember { mutableStateOf(false) }
    var showSortKeyDialog by remember { mutableStateOf(false) }

    val resultUri = Uri.parse("https://024.by/wp-content/uploads/2023/04/flag-rossii.jpg")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pixel Sorter") }) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Original")
                        Image(
                            painter = rememberAsyncImagePainter(originalUri),
                            contentDescription = "Original Image",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Result")
                        Image(
                            painter = rememberAsyncImagePainter(resultUri),
                            contentDescription = "Result Image",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Effect Level: $effectLevel")
                    Slider(
                        value = effectLevel.toFloat(),
                        onValueChange = { effectLevel = it.toInt() },
                        valueRange = 1f..100f
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showSortTypeDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Sort Type: $sortType")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showSortKeyDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Sort Key: $sortKey")
                    }
                }
                Button(
                    onClick = {
                        viewModel.updateSettings(
                            imageUri = originalUri.toString(),
                            effectLevel = effectLevel,
                            sortKey = sortKey,
                            sortType = sortType
                        )
                        viewModel.applySorting()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sort Pixels")
                }
            }
        }
    )

    if (showSortTypeDialog) {
        AlertDialog(
            onDismissRequest = { showSortTypeDialog = false },
            title = { Text("Select Sort Type") },
            text = {
                Column {
                    Text(
                        text = "Columns",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortType = SortType.ROWS
                                showSortTypeDialog = false
                            }
                    )
                    Text(
                        text = "Rows",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortType = SortType.ROWS
                                showSortTypeDialog = false
                            }
                    )
                }
            },
            confirmButton = {}
        )
    }

    if (showSortKeyDialog) {
        AlertDialog(
            onDismissRequest = { showSortKeyDialog = false },
            title = { Text("Select Sort Key") },
            text = {
                Column {
                    Text(
                        text = "Brightness",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortKey = SortKey.BRIGHTNESS
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Hue",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortKey = SortKey.HUE
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Red",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortKey = SortKey.RED
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Green",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortKey = SortKey.GREEN
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Blue",
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                            .clickable {
                                sortKey = SortKey.BLUE
                                showSortKeyDialog = false
                            }
                    )
                }
            },
            confirmButton = {}
        )
    }
}

@Preview
@Composable
fun PixelSorterPreview() {
    PixelSorterScreen()
}