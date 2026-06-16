package com.aflabs.skoola.presentation.ui.seller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.model.OrderStatus
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.presentation.components.EmptyState
import com.aflabs.skoola.presentation.viewmodel.SellerViewModel
import com.aflabs.skoola.utils.CurrencyFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
    viewModel: SellerViewModel,
    onBackClick: () -> Unit,
    onUploadProductClick: (productId: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.stats.collectAsState()
    val products by viewModel.products.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val successFlow = viewModel.sellerActionSuccess

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = successFlow) {
        successFlow.collect { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.loadSellerData()
        }
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Pesanan Masuk", "Produk Saya")

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Penjual") },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onUploadProductClick(null) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Tambah Produk")
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Total Pendapatan",
                    value = stats?.let { CurrencyFormatter.formatRupiah(it.totalRevenue) } ?: "Rp 0",
                    icon = Icons.Filled.Payments,
                    modifier = Modifier.weight(1.2f)
                )
                StatCard(
                    title = "Pesanan Masuk",
                    value = stats?.ordersCount?.toString() ?: "0",
                    icon = Icons.Filled.ReceiptLong,
                    modifier = Modifier.weight(0.8f)
                )
                StatCard(
                    title = "Produk Aktif",
                    value = products.size.toString(),
                    icon = Icons.Filled.Inventory,
                    modifier = Modifier.weight(0.8f)
                )
            }

            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                if (selectedTab == 0) {
                    if (orders.isEmpty()) {
                        EmptyState(
                            icon = Icons.Filled.ReceiptLong,
                            title = "Tidak Ada Pesanan Masuk",
                            subtitle = "Produk Anda yang dibeli oleh siswa lain akan muncul di sini."
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(orders) { order ->
                                SellerOrderItemCard(
                                    order = order,
                                    onConfirmClick = { viewModel.updateOrderStatus(order.id, OrderStatus.PROCESSING.name) },
                                    onShipClick = { viewModel.updateOrderStatus(order.id, OrderStatus.SHIPPED.name) },
                                    onCompleteClick = { viewModel.updateOrderStatus(order.id, OrderStatus.COMPLETED.name) },
                                    onCancelClick = { viewModel.updateOrderStatus(order.id, OrderStatus.CANCELLED.name) }
                                )
                            }
                        }
                    }
                } else {
                    if (products.isEmpty()) {
                        EmptyState(
                            icon = Icons.Filled.Inventory,
                            title = "Belum Ada Produk",
                            subtitle = "Mulai jual barang bekas Anda yang masih layak pakai atau jasa les di Skoola.",
                            actionText = "Unggah Produk Pertama",
                            onActionClick = { onUploadProductClick(null) }
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(products) { prod ->
                                SellerProductItemCard(
                                    product = prod,
                                    onEditClick = { onUploadProductClick(prod.id) },
                                    onDeleteClick = { viewModel.deleteProduct(prod.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SellerProductItemCard(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = CurrencyFormatter.formatRupiah(product.price),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Stok: ${product.stock} • Kategori: ${product.category.substringAfter("cat_")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onEditClick) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Filled.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun SellerOrderItemCard(
    order: Order,
    onConfirmClick: () -> Unit,
    onShipClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pesanan #${order.id.take(8).uppercase()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Penerima: ${order.buyerName}\nAlamat: ${order.shippingAddress}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = order.status.name,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            order.items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${item.product.title} x${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = CurrencyFormatter.formatRupiah(item.product.price * item.quantity), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when(order.status) {
                    OrderStatus.PENDING -> {
                        Button(onClick = onConfirmClick, modifier = Modifier.weight(1f)) {
                            Text("Terima Pesanan")
                        }
                        OutlinedButton(onClick = onCancelClick, modifier = Modifier.weight(1f)) {
                            Text("Tolak")
                        }
                    }
                    OrderStatus.PROCESSING -> {
                        Button(onClick = onShipClick, modifier = Modifier.weight(1f)) {
                            Text("Kirim / COD")
                        }
                    }
                    OrderStatus.SHIPPED -> {
                        Button(onClick = onCompleteClick, modifier = Modifier.weight(1f)) {
                            Text("Selesaikan")
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
