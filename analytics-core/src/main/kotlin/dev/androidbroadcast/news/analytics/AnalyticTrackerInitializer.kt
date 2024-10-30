package dev.androidbroadcast.news.analytics

import android.content.Context

interface AnalyticTrackerInitializer {

    fun init(context: Context): AnalyticsTracker
}
