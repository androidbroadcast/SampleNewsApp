package dev.androidbroadcast.news.main.di

actual fun createNewsMainComponent(deps: NewsMainComponent.Deps): NewsMainComponent {
    return NewsMainComponent::class.create(deps)
}
