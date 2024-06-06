package dev.androidbroadcast.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
public class NewsMainViewModel @Inject internal constructor(
    articlesRepository: ArticlesRepository
) : ViewModel() {

    private val _query: MutableStateFlow<String> = MutableStateFlow("android")

    public val query: StateFlow<String>
        get() = _query.asStateFlow()

    private val pagingConfig = PagingConfig(
        initialLoadSize = 10,
        pageSize = 10,
        maxSize = 100,
        enablePlaceholders = false,
    )

    public val state: StateFlow<PagingData<ArticleUI>> = query
        .map { query ->
            articlesRepository.allArticles(
                query,
                config = pagingConfig
            )
        }
        .flatMapConcat { pagingDataFlow ->
            pagingDataFlow.map { pagingData ->
                pagingData.map(Article::toUiArticle)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}
