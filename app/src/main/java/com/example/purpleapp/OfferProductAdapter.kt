package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class OfferProductAdapter(val data: MutableList<OfferProductData>):Adapter<OfferProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferProductViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.offer_product_item_view,parent,false)
       return OfferProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferProductViewHolder, position: Int) {
        val item = data[position]
        holder.first.text = item.offer_first
        holder.second.text = item.offer_second
        holder.img.setImageResource(item.productImage)


        holder.img.setOnClickListener {
            it.findNavController().navigate(R.id.productDescriptionFragment)
        }
    }

    override fun getItemCount()=data.size




}

class OfferProductViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.offerprodimg)
    val first:TextView = itemView.findViewById(R.id.offerprodfirst)
    val second:TextView = itemView.findViewById(R.id.offerprodsecond)
}