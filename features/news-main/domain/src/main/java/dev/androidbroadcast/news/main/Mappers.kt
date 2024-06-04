package dev.androidbroadcast.news.main

import dev.androidbroadcast.news.data.model.Article

internal fun Article.toUiArticle(): ArticleUI {
    return ArticleUI(
        id = cacheId,
        title = title,
        description = description,
        imageUrl = urlToImage,
        url = url
    )
}
