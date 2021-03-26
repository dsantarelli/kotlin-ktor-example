package handlers.products

import com.ktor.example.dao.model.Product
import dao.ProductsDao
import handlers.products.mapper.ProductMapperImpl
import handlers.products.request.UpdateProductRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.invocation.InvocationOnMock
import org.mockito.kotlin.*
import org.mockito.stubbing.Answer
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class UpdateProductTest {

    private lateinit var sut: UpdateProduct
    private lateinit var dao: ProductsDao

    @BeforeEach
    fun onBeforeEach() {
        dao = mock()
        sut = UpdateProduct(dao, ProductMapperImpl())
    }

    @Test
    fun `should not update a not existing product`() {
        val productId = "P1"

        whenever(dao.getProduct(productId))
            .thenReturn(null)

        val response = sut.handle(
            UpdateProductRequest(
                productId, "Title", "Description", 10.0
            )
        )

        assertNull(response.product)
        verify(dao).getProduct(productId)
    }

    @Test
    fun `should update an existing product`() {

        val productId = "P1"
        val now = Instant.now()
        val nowPlusSecond = Instant.now().plusSeconds(1)

        val oldProduct = Product(
            productId,
            title = "Title",
            description = "Description",
            price = 10.0,
            creationDateTime = now,
            lastUpdateDateTime = now
        )

        val updatedProduct = Product(
            productId,
            title = "Title 2",
            description = "Description 2",
            price = 11.0,
            creationDateTime = now,
            lastUpdateDateTime = nowPlusSecond
        )

        whenever(dao.getProduct(productId)).thenAnswer(object : Answer<Product?> {
            private var count = 0
            override fun answer(invocation: InvocationOnMock): Product? {
                count += 1
                return if (count == 1) oldProduct else updatedProduct
            }
        })

        doNothing().whenever(dao)
            .updateProduct(productId, updatedProduct.title, updatedProduct.description, updatedProduct.price)

        val response = sut.handle(
            UpdateProductRequest(
                productId,
                updatedProduct.title,
                updatedProduct.description,
                updatedProduct.price
            )
        )

        assertNotNull(response.product)
        val product = response.product!!
        assertEquals(productId, product.id)
        assertEquals(updatedProduct.title, product.title)
        assertEquals(updatedProduct.description, product.description)
        assertEquals(updatedProduct.price, product.price)
        assertEquals(updatedProduct.creationDateTime, product.creationDateTime)
        assertEquals(updatedProduct.lastUpdateDateTime, product.lastUpdateDateTime)

        verify(dao, times(2)).getProduct(productId)
        verify(dao).updateProduct(productId, updatedProduct.title, updatedProduct.description, updatedProduct.price)
    }
}
