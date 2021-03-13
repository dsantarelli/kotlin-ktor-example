package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.mapper.ProductMapper
import handlers.products.request.GetAllProductsRequest
import handlers.products.response.GetProductsResponse

class GetAllProducts(
    private val dao: ProductsDao,
    private val mapper: ProductMapper
) : RequestHandler<GetAllProductsRequest, GetProductsResponse> {

    override fun handle(request: GetAllProductsRequest): GetProductsResponse =
        GetProductsResponse(dao.getAllProducts().map { mapper.map(it) })
}