package com.aflabs.skoola.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.model.OrderStatus
import com.aflabs.skoola.presentation.components.EmptyState
import com.aflabs.skoola.presentation.viewmodel.CheckoutViewModel
import com.aflabs.skoola.utils.CurrencyFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(
    viewModel: CheckoutViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pesanan Saya") },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (orders.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.ReceiptLong,
                    title = "Belum Ada Transaksi",
                    subtitle = "Semua transaksi pembelian buku, seragam, les, dll. akan muncul di sini."
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(orders) { order ->
                        OrderItemCard(order = order)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(order: Order, modifier: Modifier = Modifier) {
    val dateString = remember(order.createdAt) {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
        sdf.format(Date(order.createdAt))
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ID: ${order.id.take(12).uppercase()}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dateString,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                val statusText = when(order.status) {
                    OrderStatus.PENDING -> "Menunggu"
                    OrderStatus.PROCESSING -> "Diproses"
                    OrderStatus.SHIPPED -> "Dikirim"
                    OrderStatus.COMPLETED -> "Selesai"
                    OrderStatus.CANCELLED -> "Dibatalkan"
                }

                val statusColor = when(order.status) {
                    OrderStatus.PENDING -> MaterialTheme.colorScheme.secondaryContainer
                    OrderStatus.PROCESSING -> MaterialTheme.colorScheme.primaryContainer
                    OrderStatus.SHIPPED -> MaterialTheme.colorScheme.tertiaryContainer
                    OrderStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                    OrderStatus.CANCELLED -> MaterialTheme.colorScheme.errorContainer
                }

                val contentColor = when(order.status) {
                    OrderStatus.PENDING -> MaterialTheme.colorScheme.onSecondaryContainer
                    OrderStatus.PROCESSING -> MaterialTheme.colorScheme.onPrimaryContainer
                    OrderStatus.SHIPPED -> MaterialTheme.colorScheme.onTertiaryContainer
                    OrderStatus.COMPLETED -> MaterialTheme.colorScheme.onPrimary
                    OrderStatus.CANCELLED -> MaterialTheme.colorScheme.onErrorContainer
                }

                Surface(
                    color = statusColor,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            if (order.items.isNotEmpty()) {
                order.items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.product.title} x${item.quantity}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = CurrencyFormatter.formatRupiah(item.product.price * item.quantity),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(
                    text = "Pembelian Produk/Jasa Skoola",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Pembayaran",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = CurrencyFormatter.formatRupiah(order.totalPrice + order.shippingFee),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
