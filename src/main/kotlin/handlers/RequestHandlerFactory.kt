package handlers

import handlers.products.ProductsRequestHandlerFactory

interface RequestHandlerFactory {
    val products: ProductsRequestHandlerFactory
    // TODO: you can add other request handlers here...
}