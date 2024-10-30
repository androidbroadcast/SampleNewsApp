package dev.androidbroadcast.news.analytics

import android.content.Context
import java.util.ServiceLoader

class MultiAnalyticsTracker(
     trackers: List<AnalyticsTracker>
) : AnalyticsTracker {

    private val trackers = trackers.toList()

    override fun trackEvent(event: AnalyticsTracker.Event) {
        trackers.forEach { tracker -> tracker.trackEvent(event) }
    }
}
