package dev.androidbroadcast.news.main.di

import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.main.NewsMainViewModel
import me.tatarka.inject.annotations.Component

public expect fun createNewsMainComponent(deps: NewsMainComponent.Deps): NewsMainComponent

@Component
@NewsMainScope
public abstract class NewsMainComponent(
    @Component val deps: Deps
) {

    abstract val newsMainViewModel: NewsMainViewModel

    interface Deps {

        val articlesRepository: ArticlesRepository
    }
}
