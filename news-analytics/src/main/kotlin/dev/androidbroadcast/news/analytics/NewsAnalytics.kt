@file:Suppress("unused", "SameParameterValue")

package dev.androidbroadcast.news.analytics

import android.os.Bundle
import com.amplitude.android.Amplitude
import com.google.firebase.analytics.FirebaseAnalytics
import io.appmetrica.analytics.IReporter

class NewsAnalytics(
    private val amplitude: Amplitude,
    private val appMetricaReporter: IReporter,
    private val firebase: FirebaseAnalytics,
) {

    fun articlesListLoaded() {
        firebase.logEvent(EVENT_ARTICLES_LIST_LOADED, null)
        amplitude.track(EVENT_ARTICLES_LIST_LOADED)
        appMetricaReporter.reportEvent(EVENT_ARTICLES_LIST_LOADED)
    }

    fun newsClicked(id: String) {
        firebase.logEvent(EVENT_NEWS_CLICKED, bundleOf(EVENT_NEWS_PARAM_ID, id))
        amplitude.track(EVENT_NEWS_CLICKED, eventProperties = mapOf(EVENT_NEWS_PARAM_ID, id))
        appMetricaReporter.reportEvent(EVENT_NEWS_CLICKED, mapOf(EVENT_NEWS_PARAM_ID, id))
    }

    private companion object {
        const val EVENT_NEWS_CLICKED = "newsClicked"
        const val EVENT_ARTICLES_LIST_LOADED = "articlesListLoaded"
        const val EVENT_NEWS_PARAM_ID = "id"
    }
}

private fun <K, V> mapOf(key: K, value: V): Map<K, V> {
    return buildMap(1) { put(key, value) }
}

private fun bundleOf(key: String, value: String): Bundle {
    return Bundle(1).apply { putString(key, value) }
}

