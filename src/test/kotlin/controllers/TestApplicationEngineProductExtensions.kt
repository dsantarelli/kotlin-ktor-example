package controllers

import com.fasterxml.jackson.databind.ObjectMapper
import handlers.products.request.CreateProductRequest
import handlers.products.request.UpdateProductRequest
import io.ktor.http.*
import io.ktor.server.testing.*

fun TestApplicationEngine.withGetAllProducts(action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/products")) {
        action(response)
    }
}

fun TestApplicationEngine.withGetProduct(productId: String, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/products/$productId")) {
        action(response)
    }
}

fun TestApplicationEngine.withCreateProduct(request: CreateProductRequest, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Post, "/products") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        setBody(ObjectMapper().writeValueAsString(request))
    }) {
        action(response)
    }
}

fun TestApplicationEngine.withUpdateProduct(request: UpdateProductRequest, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Put, "/products/${request.id}") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        setBody(ObjectMapper().writeValueAsString(request))
    }) {
        action(response)
    }
}

fun TestApplicationEngine.withDeleteProduct(productId: String, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Delete, "/products/$productId")) {
        action(response)
    }
}
