package handlers.products.response

import java.time.Instant

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val creationDateTime: Instant,
    val lastUpdateDateTime: Instant
)