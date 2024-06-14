package dev.androidbroadcast.news.main

import kotlinx.collections.immutable.ImmutableList

public sealed class State(public open val articles: ImmutableList<ArticleUI>?) {

    public data object None : State(articles = null)

    public class Loading(articles: ImmutableList<ArticleUI>? = null) : State(articles)

    public class Error(articles: ImmutableList<ArticleUI>? = null) : State(articles)

    public class Success(override val articles: ImmutableList<ArticleUI>) : State(articles)
}
