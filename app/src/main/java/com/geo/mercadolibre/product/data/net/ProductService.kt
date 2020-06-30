package com.geo.mercadolibre.product.data.net

import com.geo.mercadolibre.product.data.model.response.ProductApiResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET(value = "sites/{site}/search?")
    fun getProducs(@Path("site") site: String, @Query("q") query: String): Flowable<ProductApiResponse>
}