package dev.androidbroadcast.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.androidbroadcast.news.analytics.AnalyticsTracker
import dev.androidbroadcast.news.analytics.SimpleEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
public class NewsMainViewModel @Inject internal constructor(
    getAllArticlesUseCase: Provider<GetAllArticlesUseCase>,
    analyticsTracker: Lazy<AnalyticsTracker>
) : ViewModel() {

    private val analyticsTracker by analyticsTracker

    public fun onArticleClicked(article: ArticleUI) {
        analyticsTracker.trackEvent(ArticleClicked(article.id.toString()))
    }

    public fun onArticlesLoaded() {
        analyticsTracker.trackEvent(ArticlesListLoaded())
    }

    public val state: StateFlow<State> =
        getAllArticlesUseCase.get().invoke(query = "android")
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}

private operator fun <T> Lazy<T>.getValue(any: Any, property: Any): T = get()

internal class ArticlesListLoaded: SimpleEvent("articlesListLoaded")

internal class ArticleClicked(id: String): SimpleEvent("articleClicked", mapOf("id" to id))
