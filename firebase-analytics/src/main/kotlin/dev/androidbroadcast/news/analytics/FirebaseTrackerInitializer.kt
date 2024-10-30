package dev.androidbroadcast.news.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

internal class FirebaseTrackerInitializer : AnalyticTrackerInitializer {

    override fun init(context: Context): AnalyticsTracker {
        return FirebaseAnalyticsTracker(FirebaseAnalytics.getInstance(context))
    }
}
