package org.onedroid.seefood.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    var isSearchActive by mutableStateOf(false)
        private set

    var searchQuery by mutableStateOf("")
        private set

    var isSearchLoading by mutableStateOf(false)
        private set

    fun toggleSearch() {
        isSearchActive = !isSearchActive
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

}