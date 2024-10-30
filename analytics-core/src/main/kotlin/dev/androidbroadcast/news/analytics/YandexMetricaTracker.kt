package dev.androidbroadcast.news.analytics

import io.appmetrica.analytics.IReporter

class YandexMetricaTracker(
    private val reporter: IReporter,
) : AnalyticsTracker {

    override fun trackEvent(event: AnalyticsTracker.Event) {
        validate(event)
        val params = event.params
        if (params == null) {
            reporter.reportEvent(event.name)
        } else {
            reporter.reportEvent(event.name, params)
        }
    }

    private fun validate(event: AnalyticsTracker.Event) {
        require(event.name.matches(EVENT_NAME_REGEX)) {
            "Event name isn't valid for Yandex Metrica"
        }
    }

    private companion object {

        private val EVENT_NAME_REGEX = Regex("^[a-zA-Z0-9_ ]{1,40}\$\$")
    }
}
