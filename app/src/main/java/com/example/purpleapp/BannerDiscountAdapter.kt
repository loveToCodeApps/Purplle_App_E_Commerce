package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BannerDiscountAdapter(val data : List<BannerDiscountData>):Adapter<BannerDiscountViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerDiscountViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.dicount_banner_item_view,parent,false)
       return BannerDiscountViewHolder(view)

    }

    override fun onBindViewHolder(holder: BannerDiscountViewHolder, position: Int) {
        val item = data[position]
        holder.img.setImageResource(item.banner)
    }

    override fun getItemCount() = data.size

}

class BannerDiscountViewHolder(itemView: View):ViewHolder(itemView)
{
   val img : ImageView = itemView.findViewById(R.id.bannerImg)
}