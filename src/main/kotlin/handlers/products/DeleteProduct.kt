package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.request.DeleteProductRequest
import handlers.products.response.DeleteProductResponse

class DeleteProduct(private val dao: ProductsDao) :
    RequestHandler<DeleteProductRequest, DeleteProductResponse> {

    override fun handle(request: DeleteProductRequest): DeleteProductResponse {
        val product = dao.getProduct(request.id);
        return if (product == null) DeleteProductResponse(request.id, false)
        else {
            dao.deleteProduct(request.id)
            DeleteProductResponse(request.id, true)
        }
    }
}