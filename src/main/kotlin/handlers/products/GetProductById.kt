package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.mapper.ProductMapper
import handlers.products.request.GetProductByIdRequest
import handlers.products.response.GetProductByIdResponse

class GetProductById(
    private val dao: ProductsDao,
    private val mapper: ProductMapper
) : RequestHandler<GetProductByIdRequest, GetProductByIdResponse> {

    override fun handle(request: GetProductByIdRequest): GetProductByIdResponse {
        val product = dao.getProduct(request.id)
        return GetProductByIdResponse(if (product == null) null else mapper.map(product))
    }
}