package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class VerticalBannerAdapter(val data:List<VerticalBannerData>):Adapter<VerticalBannerViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalBannerViewHolder {
     val inflater = LayoutInflater.from(parent.context)
     val view = inflater.inflate(R.layout.vertical_banner_item_view,parent,false)
     return VerticalBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalBannerViewHolder, position: Int) {
        val item = data[position]
        holder.img.setImageResource(item.bannerImg)
    }

    override fun getItemCount() = data.size

}


class VerticalBannerViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.bannerImage)
}