package dev.androidbroadcast.news.core

public object ConfigProperties {
    
    public object NewsApi {
        
        public const val BaseUrl: String = "newsApi.baseUrl"
        public const val ApiKey: String = "newsApi.key"
    }
    
    internal object NewsPlatform {
        const val Debug: String = "platform.debug"
    }
}
