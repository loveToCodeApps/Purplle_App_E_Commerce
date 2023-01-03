package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class ProductDescriptionAdapter(val data : List<ProductImageData>):Adapter<ProductDescriptionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDescriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.prod_img_item_view,parent,false)
        return ProductDescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductDescriptionViewHolder, position: Int) {
       val item = data[position]
        Picasso.get().load(item.producImage).into(holder.img)

    }

    override fun getItemCount() = data.size


}

class ProductDescriptionViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.prodDescriptionImg)
}