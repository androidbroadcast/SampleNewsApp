package dev.androidbroadcast.news.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import dev.androidbroadcast.news.NewsTheme

@Composable
internal fun ArticleList(
    articleState: State.Success,
    modifier: Modifier = Modifier
) {
    ArticleList(articles = articleState.articles, modifier)
}

@Composable
internal fun ArticleList(
    articles: List<ArticleUI>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 360.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

@Composable
internal fun Article(
    article: ArticleUI,
    modifier: Modifier = Modifier
) {
    Card {
        Row(modifier.padding(bottom = 4.dp)) {
            article.imageUrl?.let { imageUrl ->
                var isImageVisible by remember { mutableStateOf(true) }
                if (isImageVisible) {
                    AsyncImage(
                        model = imageUrl,
                        onState = { state ->
                            if (state is AsyncImagePainter.State.Error) {
                                isImageVisible = false
                            }
                        },
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                val title = article.title
                if (title != null) {
                    Text(
                        text = title,
                        style = NewsTheme.typography.headlineMedium,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                val description = article.description
                if (description != null) {
                    Text(
                        text = description,
                        style = NewsTheme.typography.bodyMedium,
                        maxLines = 3
                    )
                }
            }
        }
    }
}
