package com.aflabs.skoola.presentation.ui.seller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aflabs.skoola.presentation.viewmodel.SellerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadProductScreen(
    productId: String?,
    viewModel: SellerViewModel,
    onBackClick: () -> Unit,
    onUploadSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val successFlow = viewModel.sellerActionSuccess

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var stockStr by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("cat_books") }
    var selectedCondition by remember { mutableStateOf("Baru") }
    var location by remember { mutableStateOf("Perpustakaan Pusat") }

    val categories = listOf(
        Pair("Buku", "cat_books"),
        Pair("Alat Tulis", "cat_stationery"),
        Pair("Seragam", "cat_uniforms"),
        Pair("Teknologi", "cat_tech"),
        Pair("Jasa / Les", "cat_services")
    )
    var categoryExpanded by remember { mutableStateOf(false) }

    val conditions = listOf("Baru", "Bekas - Sangat Baik", "Bekas - Baik", "Bekas - Cukup")

    LaunchedEffect(productId, products) {
        if (productId != null) {
            val prod = products.find { it.id == productId }
            if (prod != null) {
                title = prod.title
                description = prod.description
                priceStr = prod.price.toString()
                stockStr = prod.stock.toString()
                imageUrl = prod.imageUrl
                selectedCategory = prod.category
                selectedCondition = prod.condition
                location = prod.location
            }
        }
    }

    LaunchedEffect(key1 = successFlow) {
        successFlow.collect {
            onUploadSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Tambah Produk Baru" else "Edit Produk") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nama Produk / Jasa") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = priceStr,
                    onValueChange = { priceStr = it },
                    label = { Text("Harga (Rp)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = stockStr,
                    onValueChange = { stockStr = it },
                    label = { Text("Stok") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = categories.find { it.second == selectedCategory }?.first ?: "Pilih Kategori",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = {
                        IconButton(onClick = { categoryExpanded = true }) {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { categoryExpanded = true }
                )
                DropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    categories.forEach { (name, key) ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedCategory = key
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Column {
                Text(
                    text = "Kondisi Barang",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    conditions.forEach { cond ->
                        val isSelected = selectedCondition == cond
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCondition = cond },
                            label = { Text(cond) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Lokasi Ketemuan / COD (e.g. Kantin, Gd. Dekanat)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL Foto Produk (Opsional)") },
                placeholder = { Text("https://example.com/gambar.jpg") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi Lengkap") },
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val priceVal = priceStr.toLongOrNull() ?: 0L
                    val stockVal = stockStr.toIntOrNull() ?: 0
                    if (title.isNotEmpty() && priceVal > 0 && stockVal >= 0 && description.isNotEmpty()) {
                        viewModel.addOrUpdateProduct(
                            id = productId,
                            title = title,
                            description = description,
                            price = priceVal,
                            imageUrl = imageUrl,
                            category = selectedCategory,
                            stock = stockVal,
                            condition = selectedCondition,
                            location = location
                        )
                    }
                },
                enabled = !isLoading && title.isNotEmpty() && priceStr.isNotEmpty() && stockStr.isNotEmpty() && description.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = if (productId == null) "Unggah Produk" else "Simpan Perubahan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
