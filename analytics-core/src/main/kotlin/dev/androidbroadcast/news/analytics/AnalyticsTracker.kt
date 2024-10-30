package dev.androidbroadcast.news.analytics

import android.content.Context
import dev.androidbroadcast.news.analytics.AnalyticsTracker.Event
import java.util.ServiceLoader

interface AnalyticsTracker {

    val id: String

    fun trackEvent(event: Event)

    interface Event {

        val name: String

        val params: Map<String, Any>?
            get() = null

        val trackersId: List<String>?
            get() = null

        companion object {

            operator fun invoke(
                name: String,
                params: Map<String, Any>? = null
            ): Event {
                return SimpleEvent(name, params)
            }
        }
    }

    companion object {

        fun loadAvailable(context: Context): AnalyticsTracker {
            val initializers = ServiceLoader.load(AnalyticTrackerInitializer::class.java)
            val trackers = initializers.map {
                it.init(context)
            }
            return MultiAnalyticsTracker(trackers)
        }
    }
}

fun AnalyticsTracker.needToTrack(event: Event): Boolean {
    return event.trackersId?.contains(id) ?: true
}

open class SimpleEvent(
    override val name: String,
    override val params: Map<String, Any>? = null
) : Event
