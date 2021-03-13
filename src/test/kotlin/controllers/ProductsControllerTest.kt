package controllers

import AppTestBase
import handlers.products.request.CreateProductRequest
import handlers.products.request.UpdateProductRequest
import handlers.products.response.Product
import io.ktor.http.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ProductsControllerTest : AppTestBase() {

    @Test
    fun `should get an empty list of products`() = withTestApp {
        withGetAllProducts {
            assertEquals(HttpStatusCode.OK, it.status())
            val content = serializer.readValue(it.content, Map::class.java)
            assertEquals(1, content.keys.size)
            assertTrue(content.containsKey("products"))
            assertEquals(emptyList<Product>(), content["products"])
        }
    }

    @Test
    fun `should create a product`() = withTestApp {
        withCreateProduct(CreateProductRequest("Title", "Description", 10.5)) {
            assertEquals(HttpStatusCode.Created, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            assertNotNull(product)
            assertNotNull(product.id)
            assertEquals("Title", product.title)
            assertEquals("Description", product.description)
            assertEquals(10.5, product.price)
        }
    }

    @Test
    fun `should get a not empty list of products`() = withTestApp {
        var productId: String?

        withCreateProduct(CreateProductRequest("Title", "Description", 10.5)) {
            assertEquals(HttpStatusCode.Created, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            productId = product.id
            assertNotNull(productId)
        }

        withGetAllProducts {
            assertEquals(HttpStatusCode.OK, it.status())
            val content = serializer.readValue(it.content, Map::class.java)
            val products = content["products"] as List<Map<String, Any>>;
            assertEquals(1, products.size)
            val product = products.first();
            assertNotNull(product)
            assertEquals("Title", product["title"])
            assertEquals("Description", product["description"])
            assertEquals(10.5, product["price"])
        }
    }

    @Test
    fun `should get an existing product`() = withTestApp {
        var productId: String? = null

        withCreateProduct(CreateProductRequest("Title", "Description", 10.5)) {
            assertEquals(HttpStatusCode.Created, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            productId = product.id
            assertNotNull(productId)
        }

        withGetProduct(productId!!) {
            assertEquals(HttpStatusCode.OK, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            assertEquals(productId, product.id)
            assertEquals("Title", product.title)
            assertEquals("Description", product.description)
            assertEquals(10.5, product.price)
        }
    }

    @Test
    fun `should update a product`() = withTestApp {
        var productId: String? = null

        withCreateProduct(CreateProductRequest("Title", "Description", 10.5)) {
            assertEquals(HttpStatusCode.Created, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            productId = product.id
            assertNotNull(productId)
        }

        withUpdateProduct(UpdateProductRequest(productId, "Title 2", "Description 2", 11.5)) {
            assertEquals(HttpStatusCode.OK, it.status())
        }

        withGetProduct(productId!!) {
            assertEquals(HttpStatusCode.OK, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            assertEquals(productId, product.id)
            assertEquals("Title 2", product.title)
            assertEquals("Description 2", product.description)
            assertEquals(11.5, product.price)
        }
    }

    @Test
    fun `should delete a product`() = withTestApp {
        var productId: String? = null

        withCreateProduct(CreateProductRequest("Title", "Description", 10.5)) {
            assertEquals(HttpStatusCode.Created, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            productId = product.id
        }

        withGetProduct(productId!!) {
            assertEquals(HttpStatusCode.OK, it.status())
            val product = serializer.readValue(it.content, Product::class.java)
            assertEquals(productId, product.id)
        }

        withDeleteProduct(productId!!) {
            assertEquals(HttpStatusCode.NoContent, it.status())
        }

        withGetProduct(productId!!) {
            assertEquals(HttpStatusCode.NotFound, it.status())
        }
    }

    @Test
    fun `should not get a not existing product`() = withTestApp {
        withGetProduct(UUID.randomUUID().toString()) {
            assertEquals(HttpStatusCode.NotFound, it.status())
        }
    }

    @Test
    fun `should not update a not existing product`() = withTestApp {
        withUpdateProduct(UpdateProductRequest(UUID.randomUUID().toString(), "Title", "Description", 10.0)) {
            assertEquals(HttpStatusCode.NotFound, it.status())
        }
    }

    @Test
    fun `should not delete a not existing product`() = withTestApp {
        withDeleteProduct(UUID.randomUUID().toString()) {
            assertEquals(HttpStatusCode.NotFound, it.status())
        }
    }
}