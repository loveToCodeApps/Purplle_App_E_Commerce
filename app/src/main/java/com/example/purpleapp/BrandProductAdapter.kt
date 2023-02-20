package com.example.purpleapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso

class BrandProductAdapter(val data : List<BrandProductData>):Adapter<BrandProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandProductViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.brand_product_category_item_view,parent,false)
       return BrandProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandProductViewHolder, position: Int) {
        val item = data[position]
        holder.heading.text = item.brand
       // Picasso.get().load(item.img).into(holder.imge)

            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(holder.imge.context).load(item.img)
                .thumbnail(
                    Glide.with(holder.imge.context)
                        .load("https://www.pngfind.com/pngs/m/360-3604777_waiting-png-transparent-background-waiting-icon-transparent-png.png")
                )
                .apply(requestOptions).into(holder.imge)

            Glide.get(holder.imge.context).clearMemory()

            Thread(Runnable {
                // This method must be called on a background thread.
                Glide.get(holder.imge.context).clearDiskCache()
            }).start()



        holder.imge.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBrandAllProductsFragment(item.brand))
            Log.i("@@@@",item.brand)
        }
      //  holder.discountInfo.text = item.discount
    }

    override fun getItemCount() = data.size
}

class BrandProductViewHolder(itemView: View):ViewHolder(itemView)
{
    val imge:ImageView = itemView.findViewById(R.id.brand_product_img)
    val heading:TextView = itemView.findViewById(R.id.brand_title)
   // val discountInfo:TextView = itemView.findViewById(R.id.brand_discount_info)

}