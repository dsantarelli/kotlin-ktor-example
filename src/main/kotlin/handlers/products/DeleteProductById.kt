package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.request.DeleteProductByIdRequest
import handlers.products.response.DeleteProductByIdResponse

class DeleteProductById(private val dao: ProductsDao) :
    RequestHandler<DeleteProductByIdRequest, DeleteProductByIdResponse> {

    override fun handle(request: DeleteProductByIdRequest): DeleteProductByIdResponse {
        val product = dao.getProduct(request.id);
        return if (product == null) DeleteProductByIdResponse(request.id, false)
        else {
            dao.deleteProduct(request.id)
            DeleteProductByIdResponse(request.id, true)
        }
    }
}