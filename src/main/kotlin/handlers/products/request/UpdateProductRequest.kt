package handlers.products.request

data class UpdateProductRequest(
    var id: String?,
    val title: String,
    val description: String,
    val price: Double
)
