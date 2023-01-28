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
        if (item.img_name==null || item.img_name=="null" || item.img_name=="")
        {
            Picasso.get().load(R.drawable.not_available_picture).into(holder.img)
        }
        else {
            Picasso.get().load(item.image1).into(holder.img)
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
    val openProduct:Button = itemView.findViewById(R.id.textView4comboas)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.featuredConstraint)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}