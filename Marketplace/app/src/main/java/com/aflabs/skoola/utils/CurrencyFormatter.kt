package com.aflabs.skoola.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    fun formatRupiah(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        format.maximumFractionDigits = 0
        return format.format(amount).replace("Rp", "Rp ")
    }
}
