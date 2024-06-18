package dev.androidbroadcast.news.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import dev.androidbroadcast.news.NewsTheme

@Composable
internal fun ArticleList(
    articleState: State.Success,
    modifier: Modifier = Modifier
) {
    ArticleList(articles = articleState.articles, modifier)
}

@Preview
@Composable
internal fun ArticleList(
    @PreviewParameter(ArticlesPreviewProvider::class, limit = 1) articles: List<ArticleUI>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

@Preview
@Composable
internal fun Article(
    @PreviewParameter(ArticlePreviewProvider::class, limit = 1) article: ArticleUI,
    modifier: Modifier = Modifier
) {
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
                    contentDescription = stringResource(R.string.content_desc_item_article_image),
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

private class ArticlesPreviewProvider : PreviewParameterProvider<List<ArticleUI>> {
    private val articleProvider = ArticlePreviewProvider()

    override val values =
        sequenceOf(
            articleProvider.values
                .toList()
        )
}

@Suppress("MagicNumber")
private class ArticlePreviewProvider : PreviewParameterProvider<ArticleUI> {
    override val values =
        sequenceOf(
            ArticleUI(
                1,
                "Android Studio Iguana is Stable!",
                "New stable version on Android IDE has been release",
                imageUrl = null,
                url = ""
            ),
            ArticleUI(
                2,
                "Gemini 1.5 Release",
                "Upgraded version of Google AI is available",
                imageUrl = null,
                url = ""
            ),
            ArticleUI(
                3,
                "Shape animations (10 min)",
                "How to use shape transform animations in Compose",
                imageUrl = null,
                url = ""
            )
        )
}
