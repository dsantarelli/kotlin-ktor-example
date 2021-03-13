package handlers.products.response

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double
)