package dev.androidbroadcast.news.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

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
