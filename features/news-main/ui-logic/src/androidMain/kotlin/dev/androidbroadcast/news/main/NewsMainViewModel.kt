package dev.androidbroadcast.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androidbroadcast.news.data.RequestResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public class NewsMainViewModel internal constructor() : ViewModel(), KoinComponent {

    private val getAllArticlesUseCase: GetAllArticlesUseCase by inject()

    public val state: StateFlow<State> =
        getAllArticlesUseCase.invoke(query = "android")
            .map(RequestResult<List<ArticleUI>>::toState)
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}
