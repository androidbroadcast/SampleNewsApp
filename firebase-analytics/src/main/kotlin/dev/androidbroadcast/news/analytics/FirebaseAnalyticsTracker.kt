package dev.androidbroadcast.news.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsTracker(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

    override val id: String = AnalyticsTracker.FIREBASE_TRACKER_ID

    override fun trackEvent(event: AnalyticsTracker.Event) {
        if (needToTrack(event)) {
            validate(event)
            firebaseAnalytics.logEvent(event.name, event.params?.mapToBundle())
        }
    }

    private fun validate(event: AnalyticsTracker.Event) {
        require(event.name.matches(EVENT_NAME_REGEX)) {
            "Event name isn't valid for Firebase Analytics"
        }
    }

    private companion object {

        private val EVENT_NAME_REGEX = Regex("^[a-zA-Z][a-zA-Z0-9_]{0,39}\$")
    }
}

private fun Map<String, Any>.mapToBundle(): Bundle {
    val bundle = Bundle()
    forEach { (key, value) ->
        when (value) {
            is String -> bundle.putString(key, value)
            is Int -> bundle.putInt(key, value)
            is Long -> bundle.putLong(key, value)
            is Double -> bundle.putDouble(key, value)
            is Float -> bundle.putFloat(key, value)
            is Boolean -> bundle.putBoolean(key, value)
            is Array<*> -> bundle.putStringArray(key, value as Array<String>)
            is BooleanArray -> bundle.putBooleanArray(key, value)
            is ByteArray -> bundle.putByteArray(key, value)
            is CharArray -> bundle.putCharArray(key, value)
            is CharSequence -> bundle.putCharSequence(key, value)
            is FloatArray -> bundle.putFloatArray(key, value)
            is IntArray -> bundle.putIntArray(key, value)
            is LongArray -> bundle.putLongArray(key, value)
            is ShortArray -> bundle.putShortArray(key, value)
            is Bundle -> bundle.putBundle(key, value)
            else -> throw IllegalArgumentException("Unsupported value type: ${value.javaClass}")
        }
    }
    return bundle
}
