package com.geo.mercadolibre.product.data.model.response

class Product(
    val id: String,
    val title: String,
    val price: Double,
    val condition: String,
    val thumbnail: String,
    val available_quantity: Int,
    val sold_quantity: Int,
    val shipping: Shipping
)

class Shipping(
    val free_shipping: Boolean
)
