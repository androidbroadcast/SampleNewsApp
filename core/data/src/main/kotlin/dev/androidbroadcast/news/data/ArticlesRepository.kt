package dev.androidbroadcast.news.data

import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.news.data.model.Article
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.news.database.models.ArticleDBO
import dev.androidbroadcast.newsapi.NewsApi
import dev.androidbroadcast.newsapi.models.ArticleDTO
import dev.androidbroadcast.newsapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

public class ArticlesRepository @Inject constructor(
    private val database: NewsDatabase,
    private val api: NewsApi,
    private val logger: Logger
) {
    /**
     * Получение актуальных новостей с отслеживанием состояния запроса ("Обновляется", "Успшено", "Ошибка")
     */
    public fun getAll(
        query: String,
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategy()
    ): Flow<RequestResult<List<Article>>> {
        val cachedAllArticles: Flow<RequestResult<List<Article>>> = gelAllFromDatabase()
        val remoteArticles: Flow<RequestResult<List<Article>>> = getAllFromServer(query)

        return cachedAllArticles.combine(remoteArticles, mergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is RequestResult.Success) {
                    database.articlesDao.observeAll()
                        .map { dbos -> dbos.map { it.toArticle() } }
                        .map { RequestResult.Success(it) }
                } else {
                    flowOf(result)
                }
            }
    }

    private fun getAllFromServer(query: String): Flow<RequestResult<List<Article>>> {
        val apiRequest =
            flow { emit(api.everything(query = query)) }
                .onEach { result ->
                    // Если запрос прошел успешно, сохраняем данные в локальный кэш (БД)
                    if (result.isSuccess) saveArticlesToCache(result.getOrThrow().articles)
                }
                .onEach { result ->
                    if (result.isFailure) {
                        logger.e(
                            LOG_TAG,
                            "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                        )
                    }
                }
                .map { it.toRequestResult() }

        val start = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())
        return merge(apiRequest, start)
            .map { result: RequestResult<ResponseDTO<ArticleDTO>> ->
                result.map { response -> response.articles.map { it.toArticle() } }
            }
    }

    private suspend fun saveArticlesToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDto -> articleDto.toArticleDbo() }
        database.articlesDao.insert(dbos)
    }

    private fun gelAllFromDatabase(): Flow<RequestResult<List<Article>>> {
        val dbRequest =
            database.articlesDao::getAll.asFlow()
                .map<List<ArticleDBO>, RequestResult<List<ArticleDBO>>> { RequestResult.Success(it) }
                .catch {
                    logger.e(LOG_TAG, "Error getting from database. Cause = $it")
                    emit(RequestResult.Error(error = it))
                }

        val start = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())

        return merge(start, dbRequest).map { result ->
            result.map { dbos -> dbos.map { it.toArticle() } }
        }
    }

    private companion object {
        const val LOG_TAG = "ArticlesRepository"
    }
}
