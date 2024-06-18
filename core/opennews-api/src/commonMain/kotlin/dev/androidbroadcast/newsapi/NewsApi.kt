@file:Suppress("unused")

package dev.androidbroadcast.newsapi

import androidx.annotation.IntRange
import dev.androidbroadcast.newsapi.models.ArticleDTO
import dev.androidbroadcast.newsapi.models.Language
import dev.androidbroadcast.newsapi.models.ResponseDTO
import dev.androidbroadcast.newsapi.models.SortBy
import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

/**
 * [API Documentation](https://newsapi.org/docs/get-started)
 */
class NewsApi(
    private val client: HttpClient
) {
    /**
     * API details [here](https://newsapi.org/docs/endpoints/everything)
     */
    @Suppress("LongParameterList")
    suspend fun everything(
        query: String? = null,
        from: Instant? = null,
        to: Instant? = null,
        languages: List<@JvmSuppressWildcards Language>? = null,
        sortBy: SortBy? = null,
        @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @IntRange(from = 1) page: Int = 1
    ): Result<ResponseDTO<ArticleDTO>> =
        runCatching {
            val request =
                client
                    .get("everything") {
                        parameter("q", query)
                        parameter("from", from)
                        parameter("to", to)
                        parameter("languages", languages)
                        parameter("sortBy", sortBy)
                        parameter("pageSize", pageSize)
                        parameter("page", page)
                    }
            val body = request.body<ResponseDTO<ArticleDTO>>()
            body
        }
}

fun NewsApi(
    baseUrl: String,
    apiKey: String,
    json: Json = Json.Default
): NewsApi = NewsApi(httpClient(baseUrl, apiKey, json))

private fun httpClient(
    baseUrl: String,
    apiKey: String,
    json: Json = DefaultJson
): HttpClient {
    require(baseUrl.endsWith("/")) { "baseUrl must end with '/'" }

    val client: HttpClient =
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }

            defaultRequest {
                url(baseUrl)
            }
        }

    // Заменил OkHttp intercepter на Ktor плагин для добаления API Token к запросам
    client.plugin(HttpSend).intercept { request ->
        request.parameter("apiKey", apiKey)
        execute(request)
    }

    return client
}

internal data class SimpleResponse(
    @SerialName("status") val status: String
)
