package dev.androidbroadcast.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.androidbroadcast.news.analytics.NewsAnalytics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
public class NewsMainViewModel @Inject internal constructor(
    getAllArticlesUseCase: Provider<GetAllArticlesUseCase>,
    private val analytics: NewsAnalytics
) : ViewModel() {

    public fun onArticleClicked(article: ArticleUI) {
        analytics.newsClicked(article.id.toString())
    }

    public fun onArticlesLoaded() {
        analytics.articlesListLoaded()
    }

    public val state: StateFlow<State> =
        getAllArticlesUseCase.get().invoke(query = "android")
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}
