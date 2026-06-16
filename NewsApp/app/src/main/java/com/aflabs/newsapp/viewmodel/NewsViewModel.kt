package com.aflabs.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.newsapp.data.model.Article
import com.aflabs.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchUiState = MutableStateFlow<NewsUiState>(NewsUiState.Success(emptyList()))
    val searchUiState = _searchUiState.asStateFlow()

    private val _savedArticles = MutableStateFlow<List<Article>>(emptyList())
    val savedArticles = _savedArticles.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            try {
                _uiState.value = NewsUiState.Loading
                val response = repository.getNews()
                _uiState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun searchNews(query: String) {
        if (query.isBlank()) {
            _searchUiState.value = NewsUiState.Success(emptyList())
            return
        }
        viewModelScope.launch {
            try {
                _searchUiState.value = NewsUiState.Loading
                val response = repository.searchNews(query)
                _searchUiState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _searchUiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun toggleSaveArticle(article: Article) {
        val current = _savedArticles.value.toMutableList()
        val index = current.indexOfFirst { it.title == article.title }
        if (index != -1) {
            current.removeAt(index)
        } else {
            current.add(article)
        }
        _savedArticles.value = current
    }

    fun isArticleSaved(article: Article): Boolean {
        return _savedArticles.value.any { it.title == article.title }
    }
}
