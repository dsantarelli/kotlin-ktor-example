package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.mapper.ProductMapper
import handlers.products.request.GetProductRequest
import handlers.products.response.GetProductResponse

class GetProduct(
    private val dao: ProductsDao,
    private val mapper: ProductMapper
) : RequestHandler<GetProductRequest, GetProductResponse> {

    override fun handle(request: GetProductRequest): GetProductResponse {
        val product = dao.getProduct(request.id)
        return GetProductResponse(if (product == null) null else mapper.map(product))
    }
}