package com.darekbx.mineralscatalog.ui.new

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.darekbx.mineralscatalog.ui.ErrorView
import com.darekbx.mineralscatalog.ui.Loading
import com.darekbx.mineralscatalog.ui.theme.MineralsCatalogTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewScreen(
    uri: String,
    viewModel: NewScreenViewModel = koinViewModel(),
    onClose: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(Modifier.fillMaxSize()) {
        NewItemForm(uri, onClose) { label, location, description ->
            viewModel.save(uri, label, location, description)
        }

        when (val state = uiState) {
            is NewScreenState.Error -> ErrorView(
                e = state.error,
                onClose = { viewModel.resetState() }
            )

            is NewScreenState.Loading -> Loading()
            is NewScreenState.Success -> onClose()
            else -> {}
        }
    }
}

@Composable
private fun NewItemForm(
    uri: String,
    onClose: () -> Unit = { },
    onSave: (String, String, String) -> Unit = { _, _, _ -> },
) {
    val (first, second, third, buttonSave) = remember { FocusRequester.createRefs() }

    var label by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.onSurface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = rememberAsyncImagePainter(uri),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Image"
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .focusRequester(first),
            value = label,
            onValueChange = { label = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(onNext = { second.requestFocus() }),
            label = { Text("Nazwa") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .focusRequester(second),
            value = location,
            onValueChange = { location = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(onNext = { third.requestFocus() }),
            label = { Text("Lokalizacja") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .focusRequester(third),
            value = description,
            minLines = 2,
            onValueChange = { description = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(onNext = { buttonSave.requestFocus() }),
            label = { Text("Opis") }
        )

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
                Text("Anuluj")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .focusRequester(buttonSave)
                    .focusProperties { canFocus = true },
                onClick = { onSave(label, location, description) }
            ) {
                Text("Zapisz")
            }
        }
    }
}

@Preview
@Composable
fun NewScreenPreview() {
    MineralsCatalogTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.background)) {
            NewItemForm("https://example.com/image.jpg")
        }
    }
}
