package com.geo.mercadolibre

import com.geo.mercadolibre.product.data.mapper.ProductEntityMapper
import com.geo.mercadolibre.product.data.model.response.Product
import com.geo.mercadolibre.product.data.model.response.Shipping
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapperTest {

    private lateinit var productEntityMapper: ProductEntityMapper

    @Before
    fun setUp() {
        productEntityMapper = ProductEntityMapper()
    }

    @Test
    fun `Transform Product into ProductEntity`() {
        val product = Product("ID1", "TITLE 1", 0.0, "new1", "http://img.com", 1,1, Shipping(free_shipping = true))
        val productEntity = productEntityMapper.transform(product)
        assertEquals(productEntity.title, product.title)
        assertEquals(productEntity.thumbnail, product.thumbnail)
        assertEquals(productEntity.freeShipping, product.shipping.free_shipping)
    }

    @Test
    fun `Transform Product list into Product entity list`() {
        val productList = listOf(
            Product("ID1", "TITLE 1", 0.0, "new1", "http://img.com", 1,1, Shipping(free_shipping = true)),
            Product("ID2", "TITLE 2", 0.0, "new2", "http://img2.com", 2,2, Shipping(free_shipping = false))
        )

        val productEntities = productEntityMapper.transformList(productList)
        assertEquals(productList.size, productEntities.size)
        assertEquals(productList.first().id, productEntities.first().id)
    }
}