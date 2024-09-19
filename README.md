### Module Graph

```mermaid
%%{
  init: {
    'theme': 'neutral'
  }
}%%

graph LR
  subgraph :core
    :core:common["common"]
    :core:uikit["uikit"]
  end
  subgraph :features:news-main
    :features:news-main:ui["ui"]
  end
  :shared --> :compose-app
  :compose-app --> :features:news-main:ui
  :compose-app --> :core:common
  :compose-app --> :core:uikit
  :app --> :compose-app
  :desktop --> :compose-app

classDef focus fill:#769566,stroke:#fff,stroke-width:2px,color:#fff;
class :compose-app focus
```
# Новостное приложение

Новостное приложения со [стримов Android Broadcast](https://www.youtube.com/playlist?list=PL0SwNXKJbuNmIqMPiBnXkmfugSjWePAmx)

Приложения показывает простой список новостей, которые получаются из newsapi.org

Технологический стек:
- Android SDK
- [Android Jetpack](https://developer.android.com/jetpack)
- [Kotlin 2.0](https://kotlinlang.org/docs/whatsnew20.html)
- [KotlinX Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [Dagger](https://dagger.dev) + [Hilt](https://dagger.dev/hilt/)
- [Jetpack Room](https://developer.android.com/jetpack/androidx/releases/room)
- [CoIL Image Loader](https://coil-kt.github.io/coil/)
- [Material](https://github.com/material-components/material-components-android)

Основные модули
- core:* - базовые модули приложения для работы с данными
- features:* - все фичи приложения
- app - сборка приложения