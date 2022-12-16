package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DealsAdapter(val data: MutableList<DealsData>):
    RecyclerView.Adapter<DealsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.live_products_item_view,parent,false)
        return DealsViewHolder(view)
    }



    override fun getItemCount()=data.size
    override fun onBindViewHolder(holder: DealsViewHolder, position: Int) {
        val item = data[position]
      //  holder.title.text = item.heading
        holder.sale.text = "₹"+item.sale
     //   holder.mrp.text = "₹"+item.mrp
//        holder.discount.text = item.disc
        Picasso.get().load(item.image).into(holder.img)


    }


}

class DealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.live_prod_img)
    //val title: TextView = itemView.findViewById(R.id.dealsfirst)
    //val mrp: TextView = itemView.findViewById(R.id.dealsThird)
    val sale: TextView = itemView.findViewById(R.id.live_prod_price)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}