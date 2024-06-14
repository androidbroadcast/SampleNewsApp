package dev.androidbroadcast.news.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.androidbroadcast.news.NewsTheme

@Composable
fun NewsMainScreen(modifier: Modifier = Modifier) {
    NewsMainScreen(viewModel = viewModel(), modifier = modifier)
}

@Composable
internal fun NewsMainScreen(
    viewModel: NewsMainViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val currentState = state
    NewsMainContent(currentState, modifier)
}

@Composable
private fun NewsMainContent(
    currentState: State,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        when (currentState) {
            is State.None -> Unit
            is State.Error -> ErrorMessage(currentState)
            is State.Loading -> ProgressIndicator(currentState)
            is State.Success -> ArticleList(currentState)
        }
    }
}

@Composable
private fun ErrorMessage(
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column {
        Box(
            modifier
                .fillMaxWidth()
                .background(NewsTheme.colorScheme.error)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error during update", color = NewsTheme.colorScheme.onError)
        }

        val articles = state.articles
        if (articles != null) {
            ArticleList(articles = articles, modifier = modifier)
        }
    }
}

@Composable
private fun ProgressIndicator(
    state: State.Loading,
    modifier: Modifier = Modifier,
) {
    Column {
        Box(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        val articles = state.articles
        if (articles != null) {
            ArticleList(articles = articles, modifier = modifier)
        }
    }
}
