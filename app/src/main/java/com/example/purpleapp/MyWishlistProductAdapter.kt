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

class MyWishlistProductAdapter (val data : List<MyWishlistProductData>):Adapter<MyWishlistProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWishlistProductViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.my_wishlist_product_item_view,parent,false)
       return MyWishlistProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyWishlistProductViewHolder, position: Int) {

        val item = data[position]
        Picasso.get().load(item.wishlistProdImg).into(holder.img)
        holder.price.text=item.wishlistProdPrice
        holder.cutPrice.text=item.wislistProdCutPrice
        holder.heading.text=item.wishlistProdHeading
//        holder.discount.text=item.wishlistProdDiscount
//        holder.rate.text=item.wishlistProdRating
//        holder.reviews.text=item.wishlistProdReviewNo
//        holder.likes.text=item.wishlistProdLikes
    }

    override fun getItemCount() = data.size


}


class MyWishlistProductViewHolder(itemView: View):ViewHolder(itemView)
{
   val img:ImageView = itemView.findViewById(R.id.imageView30)
   val heading:TextView = itemView.findViewById(R.id.textView71)
   val price:TextView=itemView.findViewById(R.id.textView78)
   val cutPrice:TextView=itemView.findViewById(R.id.textView81)
//       val discount:TextView=itemView.findViewById(R.id.textView82)
//        val rate : Button=itemView.findViewById(R.id.button9)
//            val reviews:TextView =itemView.findViewById(R.id.textView86)
//                val likes:TextView=itemView.findViewById(R.id.textView83)

}