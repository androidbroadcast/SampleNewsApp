package dev.androidbroadcast.news.main

import dev.androidbroadcast.news.data.RequestResult
import dev.androidbroadcast.news.data.model.Article
import kotlinx.collections.immutable.toImmutableList

internal fun RequestResult<List<ArticleUI>>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error(data?.toImmutableList())
        is RequestResult.InProgress -> State.Loading(data?.toImmutableList())
        is RequestResult.Success -> State.Success(data.toImmutableList())
    }
}

internal fun Article.toUiArticle(): ArticleUI {
    return ArticleUI(
        id = cacheId,
        title = title,
        description = description,
        imageUrl = urlToImage,
        url = url
    )
}
