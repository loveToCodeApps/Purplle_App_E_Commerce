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
        if (item.img_name=="0" || item.img_name=="null")
        {
            holder.img_name.text = item.heading
            holder.background.setBackgroundColor(Color.parseColor("#ff4646"))
            holder.img_name.visibility=View.VISIBLE

        }
        else
        {
            holder.img_name.visibility=View.GONE
        }
    }

    override fun getItemCount():Int = cate.size
}


class MyCategoryViewHolder(itemView: View) : ViewHolder(itemView)
{
val headImg:ImageView = itemView.findViewById(R.id.serviceCategoryImg)
val img_name:TextView = itemView.findViewById(R.id.textView128)
val background:ConstraintLayout = itemView.findViewById(R.id.ogLayout)



}












