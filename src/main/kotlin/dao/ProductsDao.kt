package dao

import com.ktor.example.dao.model.Product

interface ProductsDao {
    fun init()
    fun createProduct(title: String, description: String, price: Double): String
    fun updateProduct(id: String, title: String, description: String, price: Double)
    fun deleteProduct(id: String)
    fun getProduct(id: String): Product?
    fun getAllProducts(): List<Product>
}