package com.example.photosearcher.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.photosearcher.SearcherApplication
import com.example.photosearcher.data.SearcherRepository
import com.example.photosearcher.model.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SearcherUiState {
    data class Success(val photos: SearchResult) : SearcherUiState
    object Error: SearcherUiState
    object Loading: SearcherUiState
}

class SearcherViewModel(private val searcherRepository: SearcherRepository) : ViewModel() {

    var searcherUiState: SearcherUiState by mutableStateOf(SearcherUiState.Loading)
        private set

    private val _userInput = MutableStateFlow("")
    val userInput: StateFlow<String> = _userInput


    init {
        viewModelScope.launch {
            _userInput
                .debounce(1500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query != "") {
                        getPhotos(query)
                    }
                }
        }
    }

    fun updateInputQuery(value: String) {
        _userInput.update {
            value
        }
    }

    fun convertResult(searchResult: SearchResult): List<String> {
        val photos: MutableList<String> = mutableListOf()
        searchResult.items.forEach {
            val imageUrl = it.link
            photos.add(imageUrl)
        }
        val images: List<String> = photos
        return images
    }

    fun getPhotos(userQuery: String) {
        viewModelScope.launch {
            searcherUiState = SearcherUiState.Loading
            searcherUiState = try {
                SearcherUiState.Success(searcherRepository.getPhotos(userQuery))
            } catch(e: IOException) {
                Log.e("YourTag", "Ошибка: ${e.message}", e)

                e.cause?.let {
                    Log.e("YourTag", "Причина ошибки: ${it.message}", it)
                }
                SearcherUiState.Error
            } catch(e: HttpException) {
                Log.e("HTTP", "Ошибка: ${e.message}", e)
                SearcherUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SearcherApplication)
                val searcherRepository = application.container.searcherRepository
                SearcherViewModel(searcherRepository = searcherRepository)
            }
        }
    }
}