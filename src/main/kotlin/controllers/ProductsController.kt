package controllers

import handlers.products.ProductsRequestHandlerFactory
import handlers.products.request.DeleteProductByIdRequest
import handlers.products.request.GetAllProductsRequest
import handlers.products.request.GetProductByIdRequest
import handlers.products.request.UpdateProductRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.productsController(handlerFactory: ProductsRequestHandlerFactory) = route("/products") {

    get {
        call.respond(handlerFactory.getAllProducts().handle(GetAllProductsRequest()))
    }

    get("/{id}") {
        val id = call.parameters["id"]
        val product = handlerFactory.getProduct().handle(GetProductByIdRequest(id!!)).product
        if (product != null) call.respond(product)
        else call.respond(HttpStatusCode.NotFound)
    }

    post {
        val product = handlerFactory.createProduct().handle(call.receive()).product
        call.response.header(HttpHeaders.Location, "/products/${product.id}")
        call.respond(HttpStatusCode.Created, product)
    }

    put("/{id}") {
        val request = call.receive<UpdateProductRequest>();
        request.id = call.parameters["id"];
        val product = handlerFactory.updateProduct().handle(request).product
        if (product != null) call.respond(HttpStatusCode.OK)
        else call.respond(HttpStatusCode.NotFound)
    }

    delete("/{id}") {
        val id = call.parameters["id"]
        val deleted = handlerFactory.deleteProduct().handle(DeleteProductByIdRequest(id!!)).deleted
        if (deleted) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound)
    }
}
