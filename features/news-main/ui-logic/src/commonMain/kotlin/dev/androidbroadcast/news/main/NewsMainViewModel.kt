package dev.androidbroadcast.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androidbroadcast.news.data.RequestResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject

@Inject
public class NewsMainViewModel internal constructor(
    private val getAllArticlesUseCase: Lazy<GetAllArticlesUseCase>
) : ViewModel() {

    public val state: StateFlow<State> =
        getAllArticlesUseCase.value.invoke(query = "android")
            .map(RequestResult<List<ArticleUI>>::toState)
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}
