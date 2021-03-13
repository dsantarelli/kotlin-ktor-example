package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.mapper.ProductMapper
import handlers.products.request.CreateProductRequest
import handlers.products.response.CreateProductResponse

class CreateProduct(private val dao: ProductsDao, private val mapper: ProductMapper) :
    RequestHandler<CreateProductRequest, CreateProductResponse> {

    override fun handle(request: CreateProductRequest): CreateProductResponse {
        val id = dao.createProduct(
            title = request.title,
            description = request.description,
            price = request.price
        )

        return CreateProductResponse(mapper.map(dao.getProduct(id)!!))
    }
}