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


class ComboOffersAdspter(val data : MutableList<ComboOffersData>):Adapter<ComboOffersViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComboOffersViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.combo_offers_item_view,parent,false)
            return ComboOffersViewHolder(view)

    }

    override fun onBindViewHolder(holder: ComboOffersViewHolder, position: Int) {
    val item = data[position]
        holder.title.text = item.heading
        holder.price.text = "₹"+item.mrp
        holder.sale.text = "₹"+item.sale
        Picasso.get().load(item.image).into(holder.image)
        holder.layout.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }
        holder.openProduct.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }

    }

    override fun getItemCount() = data.size

}
class ComboOffersViewHolder(itemView: View):ViewHolder(itemView)
{
    val image:ImageView = itemView.findViewById(R.id.offerprodimgcombo)
    val title:TextView = itemView.findViewById(R.id.offerprodfirstcombo)
    val price:TextView = itemView.findViewById(R.id.offerProdThirdcombo)
    val sale:TextView = itemView.findViewById(R.id.offerprodsecondcombo)
    val openProduct:Button = itemView.findViewById(R.id.textView4comboas)
    val layout:ConstraintLayout = itemView.findViewById(R.id.comboConstraint)


}