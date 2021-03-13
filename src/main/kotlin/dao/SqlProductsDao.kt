package dao

import com.ktor.example.dao.model.Product
import dao.tables.ProductsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class SqlProductsDao(private val db: Database) : ProductsDao {

    override fun init() = transaction(db) {
        SchemaUtils.create(ProductsTable)
    }

    override fun createProduct(title: String, description: String, price: Double): String = transaction(db) {
        val id = UUID.randomUUID().toString()
        ProductsTable.insert {
            it[ProductsTable.id] = id
            it[ProductsTable.title] = title
            it[ProductsTable.description] = description
            it[ProductsTable.price] = price
        }
        id
    }

    override fun updateProduct(id: String, title: String, description: String, price: Double) = transaction(db) {
        ProductsTable.update({ ProductsTable.id eq id }) {
            it[ProductsTable.title] = title
            it[ProductsTable.description] = description
            it[ProductsTable.price] = price
        }
        Unit
    }

    override fun deleteProduct(id: String) = transaction(db) {
        ProductsTable.deleteWhere { ProductsTable.id eq id }
        Unit
    }

    override fun getProduct(id: String): Product? = transaction(db) {
        ProductsTable
            .select { ProductsTable.id eq id }
            .map { toProduct(it) }
            .singleOrNull()
    }

    override fun getAllProducts(): List<Product> = transaction(db) {
        ProductsTable
            .selectAll()
            .map { toProduct(it) }
    }

    private fun toProduct(it: ResultRow): Product {
        return Product(
            it[ProductsTable.id],
            it[ProductsTable.title],
            it[ProductsTable.description],
            it[ProductsTable.price]
        )
    }
}