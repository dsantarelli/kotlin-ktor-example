package handlers.products

import com.ktor.example.dao.model.Product
import dao.ProductsDao
import handlers.products.request.DeleteProductRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant

internal class DeleteProductTest {

    private lateinit var sut: DeleteProduct
    private lateinit var dao: ProductsDao

    @BeforeEach
    fun onBeforeEach() {
        dao = mock()
        sut = DeleteProduct(dao)
    }

    @Test
    fun `should delete an existing product`() {
        val productId = "P1"
        val now = Instant.now()

        val productDao = Product(
            productId,
            title = "Title",
            description = "Description",
            price = 10.0,
            creationDateTime = now,
            lastUpdateDateTime = now
        )

        whenever(dao.getProduct(productId)).thenReturn(productDao)
        doNothing().whenever(dao).deleteProduct(productId)

        val response = sut.handle(DeleteProductRequest(productId))

        assertEquals(productId, response.id)
        assertTrue(response.deleted)

        verify(dao).deleteProduct(productId)
        verify(dao).getProduct(productId)
    }

    @Test
    fun `should not delete a not existing product`() {
        val productId = "P1"

        whenever(dao.getProduct(productId)).thenReturn(null)

        val response = sut.handle(DeleteProductRequest(productId))

        assertEquals(productId, response.id)
        assertFalse(response.deleted)

        verify(dao).getProduct(productId)
    }
}