package handlers.products

import com.ktor.example.dao.model.Product
import dao.ProductsDao
import handlers.products.mapper.ProductMapperImpl
import handlers.products.request.CreateProductRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant

internal class CreateProductTest {

    private lateinit var sut: CreateProduct
    private lateinit var dao: ProductsDao

    @BeforeEach
    fun onBeforeEach() {
        dao = mock()
        sut = CreateProduct(dao, ProductMapperImpl())
    }

    @Test
    fun `should create a product`() {

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

        whenever(dao.createProduct(title, description, price))
            .thenReturn(productId)

        whenever(dao.getProduct(productId))
            .thenReturn(productDao)

        val response = sut.handle(CreateProductRequest(title, description, price))

        assertEquals(productId, response.product.id)
        assertEquals(title, response.product.title)
        assertEquals(description, response.product.description)
        assertEquals(price, response.product.price)
        assertEquals(now, response.product.creationDateTime)
        assertEquals(now, response.product.lastUpdateDateTime)

        verify(dao).createProduct(title, description, price)
        verify(dao).getProduct(productId)
    }
}