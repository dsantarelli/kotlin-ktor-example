package config

import controllers.productsController
import handlers.RequestHandlerFactory
import io.ktor.routing.*

fun Routing.setup(handlerFactory: RequestHandlerFactory) {
    productsController(handlerFactory.products)
    // TODO: you can add other routing groups here...
}