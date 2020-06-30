package com.geo.mercadolibre.product.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geo.mercadolibre.R
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import kotlinx.android.synthetic.main.layout_list_product.view.*

typealias Click = (ProductEntity, View) -> Unit
class ProductListAdapter(val onClickListener: Click): RecyclerView.Adapter<ProductListAdapter.ProductsViewHolder>() {

    private lateinit var context: Context
    private var data: MutableList<ProductEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        context = parent.context
        return ProductsViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_list_product, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<ProductEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun swapDataAdd(data: List<ProductEntity>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    inner class ProductsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(product: ProductEntity) = with(itemView) {
            Glide.with(context)
                .asBitmap()
                .load(product.thumbnail)
                .into(image_product)

            txv_price.text = "$ ${product.price.toString()}"
            txv_title.text = product.title
            txv_shipping.text = if (product.freeShipping) context.getString(R.string.free_shipping) else ""

            setOnClickListener { onClickListener(product, image_product) }
            ViewCompat.setTransitionName(image_product, product.id)
        }
    }
}