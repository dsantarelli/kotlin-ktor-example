package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.mapper.ProductMapper
import handlers.products.request.UpdateProductRequest
import handlers.products.response.UpdateProductResponse

class UpdateProduct(private val dao: ProductsDao, private val mapper: ProductMapper) :
    RequestHandler<UpdateProductRequest, UpdateProductResponse> {

    override fun handle(request: UpdateProductRequest): UpdateProductResponse {
        val productId = request.id!!
        val product = dao.getProduct(productId);
        return if (product == null) UpdateProductResponse(null)
        else {
            dao.updateProduct(
                id = productId,
                title = request.title,
                description = request.description,
                price = request.price
            )

            return UpdateProductResponse(mapper.map(dao.getProduct(productId)!!))
        }
    }
}
