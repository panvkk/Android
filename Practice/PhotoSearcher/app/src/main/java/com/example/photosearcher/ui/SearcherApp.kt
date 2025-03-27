package com.example.photosearcher.ui

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photosearcher.SearcherApplication
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photosearcher.R
import com.example.photosearcher.ui.screens.EditFieldPreview
import com.example.photosearcher.ui.screens.EditQueryField
import com.example.photosearcher.ui.screens.HomeScreen

import com.example.photosearcher.ui.screens.SearcherViewModel

@Composable
fun SearcherApp() {

    val searcherViewModel: SearcherViewModel =
        viewModel(factory = SearcherViewModel.Factory)

    val currentInput by searcherViewModel.userInput.collectAsState()

    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.photo_searcher),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 32.dp, start = 12.dp, bottom = 12.dp)
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            EditQueryField(
                value = currentInput,
                onValueChange = { searcherViewModel.updateInputQuery(it) }
            )
            HomeScreen(
                searcherUiState = searcherViewModel.searcherUiState,
                searcherViewModel = searcherViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}