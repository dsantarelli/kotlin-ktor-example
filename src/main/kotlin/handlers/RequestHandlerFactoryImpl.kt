package handlers

import handlers.products.ProductsRequestHandlerFactory

class RequestHandlerFactoryImpl(
    override val products: ProductsRequestHandlerFactory
) : RequestHandlerFactory {
}