package com.example.pixelsorting.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixelSortingScreen(
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory)
) {

    var showSortTypeDialog by remember { mutableStateOf(false) }
    var showSortKeyDialog by remember { mutableStateOf(false) }
//==================================================================================================
    val uiState by viewModel.sortingUiState.collectAsState()
    val currentSettings by viewModel.settings.collectAsState()
//==================================================================================================
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if(uri != null) {
            viewModel.updateSettings(
                imageUri = uri.toString()
            )
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            imagePickerLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission declined", Toast.LENGTH_SHORT).show()
        }
    }
//==================================================================================================
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
                            painter = rememberAsyncImagePainter(currentSettings.imageUri),
                            contentDescription = "Original Image",
                            modifier = Modifier
                                .size(150.dp)
                                .clickable {
                                    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                                        imagePickerLauncher.launch("image/*")
                                    } else {
                                        permissionLauncher.launch(permission)
                                    }
                                }
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Result")
                        Image(
                            painter = rememberAsyncImagePainter(
                                when (val state = uiState) {
                                    is SortingUiState.Complete -> state.outputUri.toUri()
                                    else -> null
                                }
                            ),
                            contentDescription = "Result Image",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Effect Level: ${currentSettings.effectLevel}")
                    Slider(
                        value = currentSettings.effectLevel.toFloat(),
                        onValueChange = {
                            viewModel.updateSettings(effectLevel = it.toInt())
                        },
                        valueRange = 1f..100f
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showSortTypeDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Sort Type: ${currentSettings.sortType}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showSortKeyDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Sort Key: ${currentSettings.sortKey}")
                    }
                }
                when(val state = uiState) {
                    is SortingUiState.Default -> {
                        Button(
                            onClick = {
                                viewModel.applySorting()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Sort Pixels")
                        }
                    }
                    is SortingUiState.Loading -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            LinearProgressIndicator(modifier = Modifier.padding(8.dp))
                            FilledTonalButton(
                                onClick = {
                                    viewModel.cancelSorting()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Cancel Sorting")
                            }
                        }

                    }
                    is SortingUiState.Complete -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            ElevatedButton(
                                onClick = {
                                    viewModel.saveImage(state.outputUri)
                                },
                                modifier = Modifier.padding(bottom = 8.dp).width(175.dp)
                            ) {
                                Text("Save Image")
                            }
                            Button(
                                onClick = {
                                    viewModel.applySorting()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Sort Pixels")
                            }
                        }
                    }
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortType = SortType.COLUMNS)
                                showSortTypeDialog = false
                            }
                    )
                    Text(
                        text = "Rows",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortType = SortType.ROWS)
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortKey = SortKey.BRIGHTNESS)
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Hue",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortKey = SortKey.HUE)
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Red",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortKey = SortKey.RED)
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Green",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortKey = SortKey.GREEN)
                                showSortKeyDialog = false
                            }
                    )
                    Text(
                        text = "Blue",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateSettings(sortKey = SortKey.BLUE)
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
    PixelSortingScreen()
}