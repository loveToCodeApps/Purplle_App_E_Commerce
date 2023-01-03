package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class CategoryAdapter(var cate:List<CategoryData>):Adapter<MyCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.category_item_view,parent,false)
    return MyCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {

        val item = cate[position]
        Picasso.get().load(item.image).into(holder.headImg)
        holder.headImg.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryHomeAllProductsFragment(item.heading))

        }
    }

    override fun getItemCount():Int = cate.size
}


class MyCategoryViewHolder(itemView: View) : ViewHolder(itemView)
{
val headImg:ImageView = itemView.findViewById(R.id.category_img)
}