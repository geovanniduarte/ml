package com.geo.mercadolibre.base.mapper

/**
 * Created by Geo on 26/06/2020.
 */
interface Mapper<in R, out T> {

    fun transform(input: R): T
    fun transformList(inputList: List<R>): List<T>

}