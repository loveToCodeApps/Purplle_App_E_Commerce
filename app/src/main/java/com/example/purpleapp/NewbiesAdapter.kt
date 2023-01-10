package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class NewbiesAdapter(val data: MutableList<ViewAllProductsData>):Adapter<NewbiesViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewbiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_all_products_item_view,parent,false)
        return NewbiesViewHolder(view)
    }

    override fun getItemCount()=data.size


    override fun onBindViewHolder(holder: NewbiesViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.heading
        // holder.sale.text = "₹"+item.sale
        holder.mrp.text = "₹"+item.sale
        holder.sale.text = "₹"+item.mrp
//        holder.discount.text = item.disc
        Picasso.get().load(item.image1).into(holder.img)


        holder.openProd.setOnClickListener {
            it.findNavController().navigate(NewbiesFragmentDirections.actionNewbiesFragmentToProductDescriptionFragment(item.id))

        }

        holder.constraint.setOnClickListener {
            it.findNavController().navigate(NewbiesFragmentDirections.actionNewbiesFragmentToProductDescriptionFragment(item.id))

        }    }


}

class NewbiesViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.viewAllHomeprodimg)
    val title:TextView = itemView.findViewById(R.id.viewAllHomeprodfirst)
    val mrp:TextView = itemView.findViewById(R.id.viewAllHomeprodsecond)
    val sale:TextView = itemView.findViewById(R.id.offerProdThir)
    val openProd:TextView = itemView.findViewById(R.id.textView4com)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.myConstraint)


    //  val sale:TextView = itemView.findViewById(R.id.categprodsecond)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}