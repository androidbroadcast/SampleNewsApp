package dev.androidbroadcast.news.analytics

import com.amplitude.android.Amplitude
import com.amplitude.core.events.EventOptions

class AmplitudeAnalyticsTracker(
    private val amplitude: Amplitude,
) : AnalyticsTracker {

    override fun trackEvent(event: AnalyticsTracker.Event) {
        validate(event)
        amplitude.track(
            event.name,
            eventProperties = event.params?.toMap()
        )
    }

    private fun validate(event: AnalyticsTracker.Event) {
        require(event.name.matches(EVENT_NAME_REGEX)) {
            "Event name isn't valid for Amplitude Analytics"
        }
    }

    private companion object {

        private val EVENT_NAME_REGEX = Regex("^[a-zA-Z0-9_ ]{1,40}\$\$")
    }
}
