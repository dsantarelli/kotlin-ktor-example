package handlers.products.request

data class CreateProductRequest(
    val title: String,
    val description: String,
    val price: Double
)