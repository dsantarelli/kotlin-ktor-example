package handlers.products

import com.ktor.example.dao.model.Product
import dao.ProductsDao
import handlers.products.mapper.ProductMapperImpl
import handlers.products.request.GetAllProductsRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant
import kotlin.test.assertEquals

internal class GetAllProductsTest {

    private lateinit var sut: GetAllProducts
    private lateinit var dao: ProductsDao

    @BeforeEach
    fun onBeforeEach() {
        dao = mock()
        sut = GetAllProducts(dao, ProductMapperImpl())
    }

    @Test
    fun `should get an empty list of products`() {
        whenever(dao.getAllProducts())
            .thenReturn(emptyList())

        val response = sut.handle(GetAllProductsRequest())

        assertEquals(0, response.products.size)
        verify(dao).getAllProducts()
    }

    @Test
    fun `should get a not empty list of products`() {

        val productId = "P1"
        val title = "Title"
        val description = "Description"
        val price = 10.0
        val now = Instant.now()

        val productDao = Product(
            productId,
            title,
            description,
            price,
            creationDateTime = now,
            lastUpdateDateTime = now
        )

        whenever(dao.getAllProducts())
            .thenReturn(listOf(productDao))

        val response = sut.handle(GetAllProductsRequest())

        assertEquals(1, response.products.size)
        val product = response.products.first()
        assertEquals(productId, product.id)
        assertEquals(title, product.title)
        assertEquals(description, product.description)
        assertEquals(price, product.price)
        assertEquals(now, product.creationDateTime)
        assertEquals(now, product.lastUpdateDateTime)
    }
}