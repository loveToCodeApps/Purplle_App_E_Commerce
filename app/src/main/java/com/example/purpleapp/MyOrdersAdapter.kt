package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.SimpleDateFormat
import java.util.*


class MyOrdersAdapter(val data : List<MyOrdersData>):Adapter<MyOrdersViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrdersViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.my_orders_product_item_view,parent,false)
       return MyOrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrdersViewHolder, position: Int) {
        val item = data[position]
//        var dateString = "2021-05-12T12:12:12.121Z";
//        var odt = OffsetDateTime.parse(dateString);
//        var dtf = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH);
        val parser = SimpleDateFormat("yyyy-MM-ddHH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val output: String = formatter.format(parser.parse(item.created_on))
        holder.invoice_id.text = item.invoice_no.toString()
        holder.created_on.text = output
        holder.total_price.text = "₹"+item.total_price.toString()
        holder.status.text = item.status

        holder.viewOrder.setOnClickListener {
            it.findNavController().navigate(MyOrdersFragmentDirections.actionMyOrdersFragmentToViewOrderFragment(item.invoice_no,output,"₹"+item.total_price))
        }
       // holder.img.setImageResource(item.myOrdersProdImg)
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

    val invoice_id: TextView = itemView.findViewById(R.id.textView113)
   val created_on: TextView =itemView.findViewById(R.id.textView114)
    val total_price: TextView =itemView.findViewById(R.id.textView116)
    val status: TextView =itemView.findViewById(R.id.textView88)
    val viewOrder: Button =itemView.findViewById(R.id.button10)

    //  val discount: TextView =itemView.findViewById(R.id.textView91)
   // val rate : Button =itemView.findViewById(R.id.button12)
  ////  val reviews: TextView =itemView.findViewById(R.id.textView94)
 //   val likes: TextView =itemView.findViewById(R.id.textView92)

}