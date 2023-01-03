package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class BrandProductAdapter(val data : List<BrandProductData>):Adapter<BrandProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandProductViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.brand_product_category_item_view,parent,false)
       return BrandProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandProductViewHolder, position: Int) {
        val item = data[position]
        holder.heading.text = item.brand
        Picasso.get().load(item.img).into(holder.imge)

        holder.imge.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBrandAllProductsFragment(item.brand))
        }
      //  holder.discountInfo.text = item.discount
    }

    override fun getItemCount() = data.size
}

class BrandProductViewHolder(itemView: View):ViewHolder(itemView)
{
    val imge:ImageView = itemView.findViewById(R.id.brand_product_img)
    val heading:TextView = itemView.findViewById(R.id.brand_title)
   // val discountInfo:TextView = itemView.findViewById(R.id.brand_discount_info)

}