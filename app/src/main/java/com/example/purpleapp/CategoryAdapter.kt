package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CategoryAdapter(var cate:List<CategoryData>):Adapter<MyCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.category_item_view,parent,false)
    return MyCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {

        val item = cate[position]
        holder.header.text = item.heading
        holder.headImg.setImageResource(item.image)
    }

    override fun getItemCount():Int = cate.size
}


class MyCategoryViewHolder(itemView: View) : ViewHolder(itemView)
{
val header:TextView = itemView.findViewById(R.id.category_heading)
val headImg:ImageView = itemView.findViewById(R.id.category_img)
}