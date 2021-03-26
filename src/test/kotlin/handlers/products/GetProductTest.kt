package handlers.products

import com.ktor.example.dao.model.Product
import dao.ProductsDao
import handlers.products.mapper.ProductMapperImpl
import handlers.products.request.GetProductRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class GetProductTest {

    private lateinit var sut: GetProduct
    private lateinit var dao: ProductsDao

    @BeforeEach
    fun onBeforeEach() {
        dao = mock()
        sut = GetProduct(dao, ProductMapperImpl())
    }

    @Test
    fun `should not get a not existing product`() {
        val productId = "P1"

        whenever(dao.getProduct(productId)).thenReturn(null)

        val response = sut.handle(GetProductRequest(productId))

        assertNull(response.product)
        verify(dao).getProduct(productId)
    }

    @Test
    fun `should get an existing product`() {

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

        whenever(dao.getProduct(productId)).thenReturn(productDao)

        val response = sut.handle(GetProductRequest(productId))

        assertNotNull(response.product)
        val product = response.product!!
        assertEquals(productId, product.id)
        assertEquals(title, product.title)
        assertEquals(description, product.description)
        assertEquals(price, product.price)
        assertEquals(now, product.creationDateTime)
        assertEquals(now, product.lastUpdateDateTime)

        verify(dao).getProduct(productId)
    }
}