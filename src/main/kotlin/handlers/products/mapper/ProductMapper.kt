package handlers.products.mapper

import handlers.products.response.Product
import org.mapstruct.Mapper
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
interface ProductMapper {

    @Mappings
    fun map(product: com.ktor.example.dao.model.Product): Product
}