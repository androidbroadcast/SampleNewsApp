package dev.androidbroadcast.news.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.androidbroadcast.news.NewsTheme
import dev.androidbroadcast.news.main.NewsMainScreen
import org.koin.compose.KoinContext

@Composable
public fun NewsApp() {
    NewsTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            KoinContext {
                NewsMainScreen()
            }
        }
    }
} 