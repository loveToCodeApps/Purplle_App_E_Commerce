package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso

class NewArrivalsAdapter(val data: MutableList<NewArrivalsData>):
    RecyclerView.Adapter<NewArrivalsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.new_arrivals_item_view,parent,false)
        return NewArrivalsViewHolder(view)
    }


    override fun getItemCount()=data.size
    override fun onBindViewHolder(holder: NewArrivalsViewHolder, position: Int) {
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
        if (item.name==null || item.name=="null" || item.name=="")
        {
         //   Picasso.get().load(R.drawable.not_available_picture).into(holder.img)
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
          //  Picasso.get().load(item.image).into(holder.img)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(holder.img.context).load(item.image)
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
        holder.open.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }

    }

}

class NewArrivalsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.newArrivalimg)
    val title: TextView = itemView.findViewById(R.id.newArrivalfirst)
    val mrp: TextView = itemView.findViewById(R.id.newArrivalThirdscad)
    val sale: TextView = itemView.findViewById(R.id.newArrivalsecond)
    val open: Button = itemView.findViewById(R.id.textView4comboas)
    val disc: TextView = itemView.findViewById(R.id.disc)

    val line:View = itemView.findViewById(R.id.view4)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.newArrivalsConstraint)

//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}