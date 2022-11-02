package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class LiveProductAdapter(val data:List<LiveProductData>):Adapter<LiveProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.live_products_item_view,parent,false)
        return LiveProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: LiveProductViewHolder, position: Int) {
        val item = data[position]

        holder.price.text = item.product_price
        holder.im.setImageResource(item.product_image)
    }

    override fun getItemCount() = data.size



}

class LiveProductViewHolder(itemView: View):ViewHolder(itemView)
{
    val im:ImageView = itemView.findViewById(R.id.live_prod_img)
    val price:TextView = itemView.findViewById(R.id.live_prod_price)


}