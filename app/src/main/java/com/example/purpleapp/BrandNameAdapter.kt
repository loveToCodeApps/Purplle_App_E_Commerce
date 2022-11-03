package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BrandNameAdapter(val data : List<BrandNameData>):Adapter<BrandNameViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandNameViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.search_brands_item_view,parent,false)
       return BrandNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandNameViewHolder, position: Int) {
        val item = data[position]
        holder.names.text = item.brand

    }

    override fun getItemCount() = data.size


}

class BrandNameViewHolder(itemView: View):ViewHolder(itemView)
{
    val names: TextView = itemView.findViewById(R.id.brandNames)
}