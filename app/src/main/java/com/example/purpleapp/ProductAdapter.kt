package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class ProductAdapter(val data:List<ProductData>) : Adapter<MyProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val view = inflater.inflate(R.layout.product_item_view,parent,false)
      return MyProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        val item = data[position]
        holder.first.text = item.offer_first
        holder.second.text = item.offer_second
        holder.img.setImageResource(item.productImage)

//        holder.img.setOnClickListener {
//            Snackbar.make(,"${item.offer_first.toString()}",Snackbar.LENGTH_SHORT)
//        }


    }

    override fun getItemCount() = data.size

}

class MyProductViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.offerForYouImg)
    val first:TextView = itemView.findViewById(R.id.offerForYouFirst)
    val second:TextView = itemView.findViewById(R.id.offerForYouSecond)
}

