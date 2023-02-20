package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso


class ComboOffersAdspter(val data : MutableList<ComboOffersData>):Adapter<ComboOffersViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComboOffersViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.combo_offers_item_view,parent,false)
            return ComboOffersViewHolder(view)

    }

    override fun onBindViewHolder(holder: ComboOffersViewHolder, position: Int) {
    val item = data[position]
        holder.title.text = item.heading
        if (item.mrp==item.sale)
        {
            holder.sale.visibility = View.VISIBLE
            holder.sale.text = "₹"+item.sale
            holder.price.visibility = View.GONE
            holder.disc.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        else
        {

            holder.price.text = "₹"+item.mrp
            holder.sale.text = "₹"+item.sale
            holder.disc.text = (item.disc).take(2)+"%off"
        }

        if (item.img_name==null || item.img_name=="null" || item.img_name=="")
        {
           // Picasso.get().load(R.drawable.not_available_picture).into(holder.image)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(holder.image.context).load(R.drawable.not_available_picture)
                .thumbnail(Glide.with(holder.image.context).load("https://www.pngfind.com/pngs/m/360-3604777_waiting-png-transparent-background-waiting-icon-transparent-png.png"))
                .apply(requestOptions).into(holder.image)

            Glide.get(holder.image.context).clearMemory()

            Thread(Runnable {
                // This method must be called on a background thread.
                Glide.get(holder.image.context).clearDiskCache()
            }).start()

        }
        else {
           // Picasso.get().load(item.image).into(holder.image)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(holder.image.context).load(item.image)
                .thumbnail(Glide.with(holder.image.context).load("https://www.pngfind.com/pngs/m/360-3604777_waiting-png-transparent-background-waiting-icon-transparent-png.png"))
                .apply(requestOptions).into(holder.image)

            Glide.get(holder.image.context).clearMemory()

            Thread(Runnable {
                // This method must be called on a background thread.
                Glide.get(holder.image.context).clearDiskCache()
            }).start()
        }
        holder.layout.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }
        holder.openProduct.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }

    }

    override fun getItemCount() = data.size

}
class ComboOffersViewHolder(itemView: View):ViewHolder(itemView)
{
    val image:ImageView = itemView.findViewById(R.id.offerprodimgcombo)
    val title:TextView = itemView.findViewById(R.id.offerprodfirstcombo)
    val price:TextView = itemView.findViewById(R.id.offerProdThirdcombo)
    val sale:TextView = itemView.findViewById(R.id.offerprodsecondcombo)
    val disc:TextView = itemView.findViewById(R.id.comboDisc)
    val openProduct:Button = itemView.findViewById(R.id.textView4comboas)
    val layout:ConstraintLayout = itemView.findViewById(R.id.comboConstraint)
    val line:View = itemView.findViewById(R.id.view4combo)


}