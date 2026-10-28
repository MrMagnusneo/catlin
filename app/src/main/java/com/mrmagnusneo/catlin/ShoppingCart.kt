package com.mrmagnusneo.catlin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class CartProduct(val id: Int, val name: String, val price: Int)

/** Задание 10: сумма выводится из состояния списка, а не накапливается при рекомпозиции. */
@Composable
fun ShoppingCart() {
    var products by remember {
        mutableStateOf(listOf(CartProduct(1, "Товар №1", 100), CartProduct(2, "Товар №2", 150), CartProduct(3, "Товар №3", 56)))
    }
    var nextId by remember { mutableIntStateOf(4) }
    val total by remember { derivedStateOf { products.sumOf { it.price } } }
    val context = LocalContext.current
    var notified by remember { mutableStateOf(false) }

    LaunchedEffect(total) {
        if (total > 500 && !notified) {
            Toast.makeText(context, "Доставка бесплатная!", Toast.LENGTH_SHORT).show()
            notified = true
        } else if (total <= 500) {
            notified = false
        }
    }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Корзина товаров", style = MaterialTheme.typography.headlineMedium)
        products.forEach { product ->
            Text("${product.name} — ${product.price} руб.")
        }
        Text("Товаров: ${products.size}")
        Text("Товаров на сумму: ${total} рублей", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                // Чередуем цены, чтобы превышение порога можно было воспроизвести.
                val price = listOf(100, 150, 180)[(nextId - 4) % 3]
                products = products + CartProduct(nextId, "Товар №${nextId}", price)
                nextId++
            }) { Text("Добавить товар") }
            if (products.isNotEmpty()) {
                OutlinedButton(onClick = { products = products.dropLast(1) }) {
                    Text("Удалить товар")
                }
            }
        }
    }
}
