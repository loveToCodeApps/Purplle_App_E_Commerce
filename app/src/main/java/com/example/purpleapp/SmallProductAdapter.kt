package com.example.purpleapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SmallProductAdapter(val data : List<SmallProductData>):Adapter<SmallProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.small_product_layout,parent,false)
        return SmallProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmallProductViewHolder, position: Int) {
       val item = data[position]
        holder.img.setImageResource(item.smallImg)

    }

    override fun getItemCount() = data.size


}

class SmallProductViewHolder(itemView: View):ViewHolder(itemView)
{

    val img:ImageView = itemView.findViewById(R.id.smallProdImg)

}