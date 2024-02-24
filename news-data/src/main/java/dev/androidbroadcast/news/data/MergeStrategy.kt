package dev.androidbroadcast.news.data

import dev.androidbroadcast.news.data.RequestResult.Error
import dev.androidbroadcast.news.data.RequestResult.InProgress
import dev.androidbroadcast.news.data.RequestResult.Success

interface MergeStrategy<E> {

    fun merge(right: E, left: E): E
}

internal class RequestResponseMergeStrategy<T: Any> : MergeStrategy<RequestResult<T>> {

    override fun merge(right: RequestResult<T>, left: RequestResult<T>): RequestResult<T> {
        return when {
            right is InProgress && left is InProgress -> merge(right, left)
            right is Success && left is InProgress -> merge(right, left)
            right is InProgress && left is Success -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is Success && left is Error -> merge(right, left)

            else -> error("Unimplemented branch")
        }
    }

    private fun merge(cache: InProgress<T>, server: InProgress<T>): RequestResult<T> {
        return when {
            server.data != null -> InProgress(server.data)
            else -> InProgress(cache.data)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(cache: Success<T>, server: InProgress<T>): RequestResult<T> {
        return InProgress(cache.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(cache: InProgress<T>, server: Success<T>): RequestResult<T> {
        return InProgress(server.data)
    }

    private fun merge(cache: Success<T>, server: Error<T>): RequestResult<T> {
        return Error(data = cache.data, error = server.error)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(cache: Success<T>, server: Success<T>): RequestResult<T> {
        return Success(data = server.data)
    }
}