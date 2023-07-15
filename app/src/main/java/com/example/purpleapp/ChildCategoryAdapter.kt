package com.example.purpleapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.purpleapp.api.URLs
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class ChildCategoryAdapter(val data : List<SubCategoryData>,var prime:String,var cat:String,var sub:String):Adapter<ChildCategoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sub_category_item_view,parent,false)
        return ChildCategoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: ChildCategoryViewHolder, position: Int) {
        val item = data[position]


        holder.title.text = item.heading

        holder.ogLayout.setOnClickListener {
            it.findNavController().navigate(ChildCategoryFragmentDirections.actionChildCategoryFragmentToCategoryAllProductsFragment(prime,cat,sub,item.heading))
        }

    }

    override fun getItemCount() = data.size

}

class ChildCategoryViewHolder(itemView:View):ViewHolder(itemView)
{

    val title:TextView = itemView.findViewById(R.id.serviceCategoryTitle)
    val ogLayout:ConstraintLayout = itemView.findViewById(R.id.ogLayout)
}



