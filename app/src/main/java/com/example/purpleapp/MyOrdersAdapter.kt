package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class MyOrdersAdapter(val data : List<MyOrdersData>):Adapter<MyOrdersViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrdersViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.my_orders_product_item_view,parent,false)
       return MyOrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrdersViewHolder, position: Int) {
        val item = data[position]
        holder.img.setImageResource(item.myOrdersProdImg)
//        holder.price.text=item.myOrdersProdPrice
//        holder.cutPrice.text=item.myOrdersProdCutPrice
//        holder.discount.text=item.myOrdersProdDiscount
//        holder.heading.text=item.myOrdersProDesc
//        holder.rate.text=item.myOrdersProdRating
//        holder.reviews.text=item.myOrdersProdReviewNo
//        holder.likes.text=item.myOrdersProdLikes
    }

    override fun getItemCount() = data.size


}

class MyOrdersViewHolder(itemView: View):ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.imageView35)
    //val heading: TextView = itemView.findViewById(R.id.textView88)
  //  val price: TextView =itemView.findViewById(R.id.textView89)
//    val cutPrice: TextView =itemView.findViewById(R.id.textView90)
  //  val discount: TextView =itemView.findViewById(R.id.textView91)
   // val rate : Button =itemView.findViewById(R.id.button12)
  ////  val reviews: TextView =itemView.findViewById(R.id.textView94)
 //   val likes: TextView =itemView.findViewById(R.id.textView92)

}