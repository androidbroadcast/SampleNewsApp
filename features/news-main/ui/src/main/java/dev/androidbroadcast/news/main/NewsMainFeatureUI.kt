package dev.androidbroadcast.news.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
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
    val articlesItems: LazyPagingItems<ArticleUI> = viewModel.state.collectAsLazyPagingItems()
    NewsMainContent(articlesItems, modifier)
}

@Composable
private fun NewsMainContent(
    articlesItems: LazyPagingItems<ArticleUI>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        when (articlesItems.loadState.refresh) {
            is LoadState.Loading -> ProgressIndicator()
            is LoadState.Error -> ErrorMessage()
            is LoadState.NotLoading -> ArticleList(articlesItems)
        }
    }
}

@Composable
internal fun ErrorMessage() {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .background(NewsTheme.colorScheme.error)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error during update", color = NewsTheme.colorScheme.onError)
        }
    }
}

@Composable
internal fun ProgressIndicator() {
    CircularProgressIndicator(Modifier.padding(8.dp))
}
