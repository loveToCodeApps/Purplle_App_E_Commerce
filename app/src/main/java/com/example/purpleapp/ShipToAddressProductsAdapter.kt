package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class ShipToAddressProductsAdapter(val data : List<ShipToAddressProductsData>):Adapter<ShipToAddressProductsViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShipToAddressProductsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ship_to_address_products_item_view,parent,false)
        return ShipToAddressProductsViewHolder(view)

    }

    override fun onBindViewHolder(holder: ShipToAddressProductsViewHolder, position: Int) {
        val item = data[position]
//        Picasso.get().load(item.img).into(holder.img)
        holder.img.setImageResource(R.drawable.product_two);

        holder.title.text = item.heading
    }

    override fun getItemCount() = data.size

}

class ShipToAddressProductsViewHolder(itemView: View) : ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.imageView29)
    val title: TextView = itemView.findViewById(R.id.textView127)
}






