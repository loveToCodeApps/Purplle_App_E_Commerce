package com.example.purpleapp

import android.graphics.Color
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

class ServiceCategoryAdapter(val data : List<ServiceCategoryData> , val data2 : List<ServiceSubCategoryData>):Adapter<ServiceCategoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.service_category_item_view,parent,false)
        return ServiceCategoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: ServiceCategoryViewHolder, position: Int) {
        val item = data[position]
      //  Picasso.get().load(item.serviceCategoryImg).into(holder.img)



        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(holder.img.context).load(item.serviceCategoryImg).thumbnail(0.05f)
            .apply(requestOptions).into(holder.img)

        Glide.get(holder.img.context).clearMemory()

        Thread(Runnable {
            // This method must be called on a background thread.
            Glide.get(holder.img.context).clearDiskCache()
        }).start()




        if (item.serviceCategoryImgName=="0" || item.serviceCategoryImgName=="null" ||  item.serviceCategoryImgName=="")
        {
            holder.title.text = item.serviceCategoryHeading
            holder.ogLayout.setBackgroundColor(Color.parseColor("#ff4646"))
            holder.title.visibility=View.VISIBLE
            holder.newHeading.visibility=View.INVISIBLE
        }
        else {
            holder.title.visibility = View.GONE
            holder.newHeading.text = item.serviceCategoryHeading
        }
       // holder.title.text = item.serviceCategoryHeading + " ⬇"

       // val Expandable:Boolean = item.isExpandable
   //     holder.expandableLayout.visibility = if (Expandable) View.VISIBLE else View.GONE
//holder.nestedRecyclerView.
        holder.ogLayout.setOnClickListener {
it.findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCategoryAllProductsFragment(item.serviceCategoryHeading))
        }





//        holder.img.setOnClickListener {
//
//            item.isExpandable = !item.isExpandable
//            notifyItemChanged(position)


//            if (Expandable==false)
//            {
//                Expandable=true
//                notifyItemChanged(position)
//            }
//            else
//            {
//                Expandable=false
//                notifyItemChanged(position)
//            }
     //   }







        //Must do part
//        if (isExpandable)
//        {
//            show up arrow button
//        }
//        else
//        {
//            else show a down arrow button
//        }

    }

    override fun getItemCount() = data.size





}

class ServiceCategoryViewHolder(itemView: android.view.View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.serviceCategoryImg)
    val title:TextView = itemView.findViewById(R.id.serviceCategoryTitle)
    val newHeading:TextView = itemView.findViewById(R.id.textView80)

    //val linearLayout:LinearLayout = itemView.findViewById(R.id.LinearLayout)
//    val expandableLayout:ConstraintLayout = itemView.findViewById(R.id.expandable_layout)
    val ogLayout:ConstraintLayout = itemView.findViewById(R.id.ogLayout)
//    val nestedRecyclerView:RecyclerView = itemView.findViewById(R.id.nestedRecyclerview)
}



