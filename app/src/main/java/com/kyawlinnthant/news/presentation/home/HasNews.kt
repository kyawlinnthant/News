package com.kyawlinnthant.news.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kyawlinnthant.news.domain.NewsVo

@Composable
fun HasNewsView(
    modifier: Modifier = Modifier,
    data: List<NewsVo>,
    listState: LazyListState,
    onItemClicked: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,

        ) {
        items(
            items = data,
            key = { it.id }
        ) { item ->
            NewsItem(
                news = item,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
private fun NewsItem(
    news: NewsVo,
    onItemClicked: (Long) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 0.8f),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${news.url}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 220f,

                        )
                )
                .clickable {
                    onItemClicked(news.id)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Text(
                text = news.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Text(
                text = news.description,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}