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
        var quantity = 1
        Picasso.get().load(item.myCartProdImg).into(holder.img)
        holder.price.text="₹"+item.myCartProdNewPrice
        holder.cutPrice.text=item.myCartProdOrgPrice
        holder.heading.text=item.myCartProdHeading

        //plus button
        holder.plusBtn.setOnClickListener {
            quantity = quantity + 1
            holder.Qty.text = quantity.toString()
        }

        //minus button
        holder.minusBtn.setOnClickListener {
            quantity = quantity - 1
            holder.Qty.text = quantity.toString()
        }


    }


    override fun getItemCount() = data.size



}

class MyCartViewHolder(itemView: View):ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.imageView39)
    val heading: TextView = itemView.findViewById(R.id.textView96)
    val price: TextView =itemView.findViewById(R.id.textView97)
    val cutPrice: TextView =itemView.findViewById(R.id.textView104)
    val plusBtn: TextView =itemView.findViewById(R.id.textView4)
    val minusBtn: TextView =itemView.findViewById(R.id.textView109)
    val Qty: TextView =itemView.findViewById(R.id.textView101)
//    val total_price: TextView =itemView.findViewById(R.id.textView89)





    // val discount: TextView =itemView.findViewById(R.id.textView)
  //  val rate : Button =itemView.findViewById(R.id.button14)
   // val reviews: TextView =itemView.findViewById(R.id.textView102)
  //  val likes: TextView =itemView.findViewById(R.id.textView100)

}