package com.darekbx.mineralscatalog.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.darekbx.mineralscatalog.model.Mineral
import com.darekbx.mineralscatalog.ui.Loading
import com.darekbx.mineralscatalog.ui.theme.MineralsCatalogTheme
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri

@Composable
fun ListScreen(viewmodel: ListViewModel = koinViewModel(), onItemClick: (String) -> Unit) {
    val minerals by viewmodel.minerals().collectAsState(initial = null)
    Box(modifier = Modifier.fillMaxSize()) {
        minerals?.let { mineralsList ->
            if (mineralsList.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Nic tutaj nie ma :)",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                MineralsList(
                    minerals = mineralsList,
                    onItemClick = { mineral -> (onItemClick(mineral.id)) },
                )
            }
        } ?: run {
            Loading()
        }
    }
}

@Composable
fun MineralsList(
    minerals: List<Mineral>,
    onItemClick: (Mineral) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(minerals, key = { it.id }) { item ->
                MineralItem(
                    modifier = Modifier.fillMaxWidth().clickable { onItemClick(item) },
                    mineral = item
                )
            }
        }
    )
}

@Composable
fun MineralItem(
    modifier: Modifier = Modifier,
    mineral: Mineral
) {
    Card(
        modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Box(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .height(180.dp)
                        .width(120.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    painter = rememberAsyncImagePainter(mineral.photo.toUri()),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "Image"
                )

                Column(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = mineral.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = mineral.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = mineral.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                text = mineral.dateTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun MineralItemPreview() {
    MineralsCatalogTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.background).padding(8.dp)) {
            MineralItem(
                modifier = Modifier.fillMaxWidth(),
                mineral = Mineral(
                    id = "1",
                    label = "Quartz",
                    location = "Brazil",
                    description = "A common mineral composed of silicon and oxygen.",
                    dateTime = "2025-02-23",
                    photo = "https://example.com/quartz.jpg"
                )
            )
        }
    }
}