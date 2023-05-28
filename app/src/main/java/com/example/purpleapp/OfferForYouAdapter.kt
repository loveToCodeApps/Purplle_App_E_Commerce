package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class OfferForYouAdapter(val data : List<OfferForYouData>) : RecyclerView.Adapter<OfferForYouViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferForYouViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.offers_for_you_item_view,parent,false)
        return OfferForYouViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferForYouViewHolder, position: Int) {
        val item = data[position]
        holder.first.text = item.offer_for_you_first
        holder.second.text = item.offer_for_you_second
       // holder.img.setImageResource(item.offer_for_you_img)


        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(holder.img.context).load(item.offer_for_you_img).thumbnail(0.05f)
            .apply(requestOptions).into(holder.img)

        Glide.get(holder.img.context).clearMemory()

        Thread(Runnable {
            // This method must be called on a background thread.
            Glide.get(holder.img.context).clearDiskCache()
        }).start()

//        holder.img.setOnClickListener {
//            Snackbar.make(,"${item.offer_first.toString()}",Snackbar.LENGTH_SHORT)
//        }


    }

    override fun getItemCount() = data.size

}

class OfferForYouViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.offerForYouImg)
    val first: TextView = itemView.findViewById(R.id.offerForYouFirst)
    val second: TextView = itemView.findViewById(R.id.offerForYouSecond)
}

