package dev.androidbroadcast.news.main

import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.data.RequestResult
import dev.androidbroadcast.news.data.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
internal class GetAllArticlesUseCase(
    private val repository: ArticlesRepository
) {
    operator fun invoke(query: String): Flow<RequestResult<List<ArticleUI>>> {
        return repository.getAll(query).map { requestResult ->
            requestResult.map { articles -> articles.map { it.toUiArticle() } }
        }
    }
}
