package handlers.products

import handlers.RequestHandler
import handlers.products.request.*
import handlers.products.response.*

interface ProductsRequestHandlerFactory {
    fun getAllProducts(): RequestHandler<GetAllProductsRequest, GetProductsResponse>
    fun getProduct(): RequestHandler<GetProductByIdRequest, GetProductByIdResponse>
    fun createProduct(): RequestHandler<CreateProductRequest, CreateProductResponse>
    fun updateProduct(): RequestHandler<UpdateProductRequest, UpdateProductResponse>
    fun deleteProduct(): RequestHandler<DeleteProductByIdRequest, DeleteProductByIdResponse>
}