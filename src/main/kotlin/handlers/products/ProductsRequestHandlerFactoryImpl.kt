package handlers.products

import dao.ProductsDao
import handlers.RequestHandler
import handlers.products.mapper.ProductMapper
import handlers.products.request.*
import handlers.products.response.*

class ProductsRequestHandlerFactoryImpl(
    private val dao: ProductsDao,
    private val mapper: ProductMapper
) : ProductsRequestHandlerFactory {

    override fun getAllProducts(): RequestHandler<GetAllProductsRequest, GetProductsResponse> =
        GetAllProducts(dao, mapper)

    override fun getProduct(): RequestHandler<GetProductRequest, GetProductResponse> =
        GetProduct(dao, mapper)

    override fun createProduct(): RequestHandler<CreateProductRequest, CreateProductResponse> =
        CreateProduct(dao, mapper)

    override fun updateProduct(): RequestHandler<UpdateProductRequest, UpdateProductResponse> =
        UpdateProduct(dao, mapper)

    override fun deleteProduct(): RequestHandler<DeleteProductRequest, DeleteProductResponse> =
        DeleteProduct(dao)
}
