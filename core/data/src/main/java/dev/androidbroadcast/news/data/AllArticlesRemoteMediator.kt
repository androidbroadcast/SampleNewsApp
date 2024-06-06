package dev.androidbroadcast.news.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.androidbroadcast.news.database.dao.ArticleDao
import dev.androidbroadcast.news.database.models.ArticleDBO
import dev.androidbroadcast.newsapi.NewsApi
import javax.inject.Inject
import javax.inject.Provider

@ExperimentalPagingApi
public class AllArticlesRemoteMediator internal constructor(
    private val query: String,
    private val articleDao: ArticleDao,
    private val networkService: NewsApi,
) : RemoteMediator<Int, ArticleDBO>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleDBO>
    ): MediatorResult {
        val pageSize: Int = state.config.pageSize.coerceAtMost(NewsApi.MAX_PAGE_SIZE)

        val page: Int = getPage(loadType, state)
            ?: return MediatorResult.Success(endOfPaginationReached = false)

        val networkResult = networkService.everything(query, page = page, pageSize = pageSize)
        if (networkResult.isSuccess) {
            val totalResults = networkResult.getOrThrow().totalResults
            println(totalResults)
            val articlesDbo = networkResult.getOrThrow().articles.map { it.toArticleDbo() }
            if (loadType == LoadType.REFRESH) {
                articleDao.cleanAndInsert(articlesDbo)
            } else {
                articleDao.insert(articlesDbo)
            }

            return MediatorResult.Success(
                endOfPaginationReached = articlesDbo.size < pageSize
            )
        }

        return MediatorResult.Error(networkResult.exceptionOrNull() ?: UnknownError())
    }

    private fun getPage(
        loadType: LoadType,
        state: PagingState<Int, ArticleDBO>
    ): Int? = when (loadType) {
        LoadType.REFRESH ->
            state.anchorPosition?.let { state.closestPageToPosition(it) }?.prevKey ?: 1

        LoadType.PREPEND -> null

        LoadType.APPEND -> {
            val lastPage = state.pages.lastOrNull()
            if (lastPage == null) {
                1
            } else {
                state.pages.size + 1
            }
        }
    }

    public class Factory @Inject constructor(
        private val articleDao: Provider<ArticleDao>,
        private val networkService: Provider<NewsApi>,
    ) {

        public fun create(query: String): AllArticlesRemoteMediator {
            return AllArticlesRemoteMediator(
                query = query,
                articleDao = articleDao.get(),
                networkService = networkService.get()
            )
        }
    }
}
