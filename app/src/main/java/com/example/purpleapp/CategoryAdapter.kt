package com.example.purpleapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.squareup.picasso.Picasso

class CategoryAdapter(var cate:List<CategoryData>):Adapter<MyCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.category_item_view,parent,false)
    return MyCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {

        val item = cate[position]


        if (item.heading == null || item.heading == "null" || item.heading.length == 0)
        {
            holder.img_name.text = ""
        }
        else
        {
            holder.img_name.text = item.heading
        }


        Picasso.get().load(item.image).into(holder.headImg)
        if (item.img_name=="0" || item.img_name=="null" || item.img_name=="")
        {
            holder.img_name.text = item.heading
            holder.background.setBackgroundColor(Color.parseColor("#ff4646"))
            holder.img_name.visibility=View.VISIBLE
            holder.img_title.visibility=View.GONE


        }
        else
        {
            holder.img_name.visibility=View.GONE
            holder.img_title.text=item.heading.toString()

        }

        holder.headImg.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryHomeAllProductsFragment(item.heading))

        }


    }

    override fun getItemCount():Int = cate.size
}


class MyCategoryViewHolder(itemView: View) : ViewHolder(itemView)
{
val headImg:ImageView = itemView.findViewById(R.id.serviceCategoryImg)
val img_name:TextView = itemView.findViewById(R.id.textView128)
val img_title:TextView = itemView.findViewById(R.id.textView143)
val background:ConstraintLayout = itemView.findViewById(R.id.ogLayout)



}












