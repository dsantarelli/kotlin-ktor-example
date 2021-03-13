package config

import dao.ProductsDao
import dao.SqlProductsDao
import handlers.RequestHandlerFactory
import handlers.RequestHandlerFactoryImpl
import handlers.products.ProductsRequestHandlerFactory
import handlers.products.ProductsRequestHandlerFactoryImpl
import handlers.products.mapper.ProductMapper
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import org.mapstruct.factory.Mappers

fun setup(db: Database) = module {
    single<ProductMapper> { Mappers.getMapper(ProductMapper::class.java) }
    single<ProductsDao> {
        val dao = SqlProductsDao(db)
        dao.init()
        dao
    }
    single<ProductsRequestHandlerFactory> { ProductsRequestHandlerFactoryImpl(get(), get()) }
    single<RequestHandlerFactory> { RequestHandlerFactoryImpl(get()) }
}