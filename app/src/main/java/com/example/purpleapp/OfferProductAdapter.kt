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

class OfferProductAdapter(val data: MutableList<OfferProductData>):Adapter<OfferProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferProductViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.offer_product_item_view,parent,false)
       return OfferProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferProductViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.heading
        if (item.mrp==item.sale)
        {
            holder.sale.visibility = View.VISIBLE
            holder.sale.text = "₹"+item.sale
            holder.mrp.visibility = View.GONE
            holder.disc.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        else
        {

            holder.sale.text = "₹"+item.sale
            holder.mrp.text = "₹"+item.mrp
            holder.disc.text = (item.disc).take(2)+"%off"
        }







//        holder.discount.text = item.disc
        if (item.img_name==null || item.img_name=="null" || item.img_name=="")
        {
          //  Picasso.get().load(R.drawable.not_available_picture).into(holder.img)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(holder.img.context).load(R.drawable.not_available_picture)
                .thumbnail(Glide.with(holder.img.context).load("https://www.pngfind.com/pngs/m/360-3604777_waiting-png-transparent-background-waiting-icon-transparent-png.png"))
                .apply(requestOptions).into(holder.img)

            Glide.get(holder.img.context).clearMemory()

            Thread(Runnable {
                // This method must be called on a background thread.
                Glide.get(holder.img.context).clearDiskCache()
            }).start()
        }
        else {
            //Picasso.get().load(item.image1).into(holder.img)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(holder.img.context).load(item.image1)
                .thumbnail(Glide.with(holder.img.context).load("https://www.pngfind.com/pngs/m/360-3604777_waiting-png-transparent-background-waiting-icon-transparent-png.png"))
                .apply(requestOptions).into(holder.img)

            Glide.get(holder.img.context).clearMemory()

            Thread(Runnable {
                // This method must be called on a background thread.
                Glide.get(holder.img.context).clearDiskCache()
            }).start()
        }



        holder.constraint.setOnClickListener {
                    it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }
        holder.openProduct.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }


    }

    override fun getItemCount()=data.size




}

class OfferProductViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.categprodimg)
    val title:TextView = itemView.findViewById(R.id.categprodfirst)
    val mrp:TextView = itemView.findViewById(R.id.offerProdThird)
    val sale:TextView = itemView.findViewById(R.id.categprodsecond)
    val disc:TextView = itemView.findViewById(R.id.onlydisc)
    val openProduct:Button = itemView.findViewById(R.id.textView4comboas)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.featuredConstraint)
    val line:View = itemView.findViewById(R.id.view4)

//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}