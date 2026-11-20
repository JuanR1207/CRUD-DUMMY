package com.example.crud_dummyjson.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crud_dummyjson.ui.theme.CRUDDUMMYJSONTheme

// Sección con los campos del formulario. No guarda estado: solo muestra
// los valores que recibe y avisa cuando cambian.
@Composable
fun ProductFormSection(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    titleError: Boolean,
    categoryError: Boolean,
    priceError: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Título") },
            singleLine = true,
            enabled = enabled,
            isError = titleError,
            supportingText = { if (titleError) Text("El título es obligatorio") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Descripción") },
            minLines = 3,
            maxLines = 6,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = category,
            onValueChange = onCategoryChange,
            label = { Text("Categoría") },
            singleLine = true,
            enabled = enabled,
            isError = categoryError,
            supportingText = { if (categoryError) Text("La categoría es obligatoria") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = price,
            onValueChange = onPriceChange,
            label = { Text("Precio") },
            prefix = { Text("$") },
            singleLine = true,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = priceError,
            supportingText = { if (priceError) Text("Ingresa un precio válido") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductFormSectionPreview() {
    CRUDDUMMYJSONTheme {
        ProductFormSection(
            title = "Mascara", onTitleChange = {},
            description = "Volumizing mascara", onDescriptionChange = {},
            category = "beauty", onCategoryChange = {},
            price = "9.99", onPriceChange = {},
            titleError = false, categoryError = false, priceError = false,
            enabled = true
        )
    }
}
