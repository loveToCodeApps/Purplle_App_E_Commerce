package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.w3c.dom.Text

class ServiceCategoryAdapter(val data : List<ServiceCategoryData>):Adapter<ServiceCategoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.service_category_item_view,parent,false)
        return ServiceCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceCategoryViewHolder, position: Int) {
        val item = data[position]
        holder.img.setImageResource(item.serviceCategoryImg)
        holder.title.text = item.serviceCategoryHeading
    }

    override fun getItemCount() = data.size

}

class ServiceCategoryViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.serviceCategoryImg)
    val title:TextView = itemView.findViewById(R.id.serviceCategoryTitle)
}