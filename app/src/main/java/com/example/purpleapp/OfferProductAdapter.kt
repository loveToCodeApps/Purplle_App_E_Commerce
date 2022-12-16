package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

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
        holder.sale.text = "₹"+item.sale
        holder.mrp.text = "₹"+item.mrp
//        holder.discount.text = item.disc
        Picasso.get().load(item.image1).into(holder.img)


        holder.img.setOnClickListener {
            it.findNavController().navigate(R.id.productDescriptionFragment)
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
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}