package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class VerticalBannerAdapter(val data:List<VerticalBannerData>):Adapter<VerticalBannerViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalBannerViewHolder {
     val inflater = LayoutInflater.from(parent.context)
     val view = inflater.inflate(R.layout.vertical_banner_item_view,parent,false)
     return VerticalBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalBannerViewHolder, position: Int) {
        val item = data[position]

       // holder.img.setImageResource(item.bannerImg)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(holder.img.context).load(item.bannerImg).thumbnail(0.05f)
            .apply(requestOptions).into(holder.img)

        Glide.get(holder.img.context).clearMemory()

        Thread(Runnable {
            // This method must be called on a background thread.
            Glide.get(holder.img.context).clearDiskCache()
        }).start()

    }

    override fun getItemCount() = data.size

}


class VerticalBannerViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.bannerImage)
}