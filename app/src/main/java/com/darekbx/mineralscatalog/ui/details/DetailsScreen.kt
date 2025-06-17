package com.darekbx.mineralscatalog.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.darekbx.mineralscatalog.model.Mineral
import com.darekbx.mineralscatalog.ui.ErrorView
import com.darekbx.mineralscatalog.ui.Loading
import com.darekbx.mineralscatalog.ui.theme.MineralsCatalogTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    mineralId: String,
    viewModel: DetailsViewModel = koinViewModel(),
    onBack: () -> Unit = { }
) {
    val uiState by viewModel.uiState.collectAsState()
    var mineralToDelete by remember { mutableStateOf<Mineral?>(null) }

    LaunchedEffect(Unit) {
        viewModel.load(mineralId)
    }

    Box(Modifier.fillMaxSize()) {

        when (val state = uiState) {
            is DetailsState.Error -> ErrorView(
                e = state.error,
                onClose = { viewModel.resetState() }
            )
            is DetailsState.Loading -> Loading()
            is DetailsState.Success -> MineralPreview(
                state.mineral,
                onClose = onBack,
                onDelete = { mineralToDelete = it }
            )
            else -> {}
        }
    }

    mineralToDelete?.let { mineral ->
        DeleteDialog(
            mineral = mineral,
            onDismiss = { mineralToDelete = null },
            onConfirm = {
                viewModel.deleteEntry(mineral.id)
                mineralToDelete = null
                onBack()
            }
        )
    }
}

@Composable
private fun MineralPreview(
    mineral: Mineral,
    onClose: () -> Unit = { },
    onDelete: (Mineral) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = mineral.label,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = mineral.location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.onSurface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = rememberAsyncImagePainter(mineral.photo),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Image"
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = mineral.dateTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = mineral.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        Spacer(Modifier.fillMaxHeight().weight(1F))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                onClick = onClose
            ) {
                Text("Zamknij")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                onClick = { onDelete(mineral) }
            ) {
                Text("Usuń")
            }
        }
    }
}

@Preview
@Composable
fun NewScreenPreview() {
    MineralsCatalogTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.background)) {
            MineralPreview(
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
