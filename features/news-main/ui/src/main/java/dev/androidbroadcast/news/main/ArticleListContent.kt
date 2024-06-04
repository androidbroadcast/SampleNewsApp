package dev.androidbroadcast.news.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import dev.androidbroadcast.news.NewsTheme

@Composable
internal fun ArticleList(
    items: LazyPagingItems<ArticleUI>
) {
    val lazyListState = rememberLazyListState()
    Box(Modifier.fillMaxSize()) {
        LazyColumn(state = lazyListState) {
            if (items.loadState.prepend is LoadState.Loading) {
                item { ProgressIndicator() }
            }

            items(
                count = items.itemCount,
                key = items.itemKey { it.id }
            ) {
                val item = items[it]
                if (item != null) Article(item)
            }

            if (items.loadState.append is LoadState.Loading) {
                item { ProgressIndicator() }
            }
        }

        if (items.loadState.append is LoadState.Error) {
            ErrorMessage()
        }
    }
}


@Composable
internal fun Article(
    article: ArticleUI,
    modifier: Modifier = Modifier,
) {
    Row(
        Modifier
            .padding(bottom = 4.dp)
            .height(150.dp)
            .then(modifier)
    ) {
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
                    contentDescription = stringResource(R.string.content_desc_item_article_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = article.title,
                style = NewsTheme.typography.headlineMedium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = article.description,
                style = NewsTheme.typography.bodyMedium,
                maxLines = 3
            )
        }
    }
}
