@file:OptIn(ExperimentalPagingApi::class)

package dev.androidbroadcast.news.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.androidbroadcast.news.data.model.Article
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.news.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class ArticlesRepository @Inject constructor(
    private val database: NewsDatabase,
    private val remoteMediator: AllArticlesRemoteMediator.Factory,
) {

    public fun allArticles(
        query: String,
        config: PagingConfig,
    ): Flow<PagingData<Article>> {
        val pager = Pager(
            config = config,
            remoteMediator = remoteMediator.create(query),
            pagingSourceFactory = { database.articlesDao.pagingAll() }
        )
        return pager.flow
            .map { pagingData -> pagingData.map { it.toArticle() } }
    }
}
