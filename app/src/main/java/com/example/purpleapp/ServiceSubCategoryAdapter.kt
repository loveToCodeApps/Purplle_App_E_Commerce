package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class ServiceSubCategoryAdapter(val data : List<ServiceSubCategoryData>):Adapter<ServiceSubCategoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceSubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sub_category_item_view,parent,false)
        return ServiceSubCategoryViewHolder(view)
    }

//    override fun onBindViewHolder(holder: ServiceCategoryViewHolder, position: Int) {
//        val item = data[position]

//        Picasso.get().load(item.serviceCategoryImg).into(holder.img)
//        holder.title.text = item.serviceCategoryHeading + " ⬇"
//
//        val Expandable:Boolean = item.isExpandable
//        holder.expandableLayout.visibility = if (Expandable) View.VISIBLE else View.GONE
//holder.nestedRecyclerView.



//        holder.img.setOnClickListener {
//
//            item.isExpandable = !item.isExpandable
//            notifyItemChanged(position)
//
//
////            if (Expandable==false)
////            {
////                Expandable=true
////                notifyItemChanged(position)
////            }
////            else
////            {
////                Expandable=false
////                notifyItemChanged(position)
////            }
//        }
//
//
//
//
//
//
//        //Must do part
////        if (isExpandable)
////        {
////            show up arrow button
////        }
////        else
////        {
////            else show a down arrow button
////        }
//
//    }

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ServiceSubCategoryViewHolder, position: Int) {


    }


}

class ServiceSubCategoryViewHolder(itemView: android.view.View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.serviceCategoryImg)
    val title:TextView = itemView.findViewById(R.id.serviceCategoryTitle)
    //val linearLayout:LinearLayout = itemView.findViewById(R.id.LinearLayout)
    //val expandableLayout:ConstraintLayout = itemView.findViewById(R.id.expandable_layout)
    val ogLayout:ConstraintLayout = itemView.findViewById(R.id.ogLayout)
   // val nestedRecyclerView:RecyclerView = itemView.findViewById(R.id.nestedRecyclerview)
}



