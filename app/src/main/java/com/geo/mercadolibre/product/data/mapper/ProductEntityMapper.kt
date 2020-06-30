package com.geo.mercadolibre.product.data.mapper

import com.geo.mercadolibre.base.mapper.Mapper
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.model.response.Product


class ProductEntityMapper: Mapper<Product, ProductEntity> {
    override fun transform(input: Product): ProductEntity = ProductEntity(input.id, input.title, input.price, input.condition, input.thumbnail, input.available_quantity, input.sold_quantity, input.shipping.free_shipping)

    override fun transformList(inputList: List<Product>) = inputList.map { transform(it) }
}