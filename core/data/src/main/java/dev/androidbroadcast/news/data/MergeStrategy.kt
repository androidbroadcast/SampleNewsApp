@file:Suppress("UNUSED_PARAMETER")

package dev.androidbroadcast.news.data

import dev.androidbroadcast.news.data.RequestResult.Error
import dev.androidbroadcast.news.data.RequestResult.InProgress
import dev.androidbroadcast.news.data.RequestResult.Success

public interface MergeStrategy<E> {

    public fun merge(
        right: E,
        left: E
    ): E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(
        right: RequestResult<T>,
        left: RequestResult<T>
    ): RequestResult<T> {
        return when {
            right is InProgress && left is InProgress -> merge(right, left)
            right is Success && left is InProgress -> merge(right, left)
            right is InProgress && left is Success -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is Success && left is Error -> merge(right, left)
            right is InProgress && left is Error -> merge(right, left)
            right is Error && left is InProgress -> merge(right, left)
            right is Error && left is Success -> merge(right, left)

            else -> error("Unimplemented branch right=$right & left=$left")
        }
    }

    private fun merge(
        cache: InProgress<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null -> InProgress(server.data)
            else -> InProgress(cache.data)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return InProgress(cache.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: InProgress<T>,
        server: Success<T>
    ): RequestResult<T> {
        return InProgress(server.data)
    }

    private fun merge(
        cache: Success<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(data = cache.data, error = server.error)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: Success<T>
    ): RequestResult<T> {
        return Success(data = server.data)
    }

    private fun merge(
        cache: InProgress<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(data = server.data ?: cache.data, error = server.error)
    }

    private fun merge(
        cache: Error<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return server
    }

    private fun merge(
        cache: Error<T>,
        server: Success<T>
    ): RequestResult<T> {
        return server
    }
}
