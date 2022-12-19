package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso


class MyCartAdapter(val data : List<MyCartData>):Adapter<MyCartViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.my_cart_product_item_view,parent,false)
        return MyCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val item = data[position]
        Picasso.get().load(item.myCartProdImg).into(holder.img)
        holder.price.text=item.myCartProdNewPrice
        holder.cutPrice.text=item.myCartProdOrgPrice
      //  holder.discount.text=item.myCartProdDiscount
        holder.heading.text=item.myCartProdHeading
//        holder.rate.text=item.myCartProdRating
//        holder.reviews.text=item.myCartProdReviewNo
//        holder.likes.text=item.myCartProdLikes
    }

    override fun getItemCount() = data.size


}

class MyCartViewHolder(itemView: View):ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.imageView39)
    val heading: TextView = itemView.findViewById(R.id.textView96)
    val price: TextView =itemView.findViewById(R.id.textView97)
    val cutPrice: TextView =itemView.findViewById(R.id.textView104)
   // val discount: TextView =itemView.findViewById(R.id.textView)
  //  val rate : Button =itemView.findViewById(R.id.button14)
   // val reviews: TextView =itemView.findViewById(R.id.textView102)
  //  val likes: TextView =itemView.findViewById(R.id.textView100)

}