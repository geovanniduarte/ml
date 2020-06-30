package com.geo.mercadolibre.product.data.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductEntity(
    val id: String,
    val title: String,
    val price: Double,
    val condition: String,
    val thumbnail: String,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val freeShipping: Boolean,
    val discountedPrice: Double? = null
) : Parcelable