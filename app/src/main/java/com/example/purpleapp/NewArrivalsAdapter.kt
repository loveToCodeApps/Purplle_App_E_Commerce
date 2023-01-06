package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewArrivalsAdapter(val data: MutableList<NewArrivalsData>):
    RecyclerView.Adapter<NewArrivalsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.new_arrivals_item_view,parent,false)
        return NewArrivalsViewHolder(view)
    }



    override fun getItemCount()=data.size
    override fun onBindViewHolder(holder: NewArrivalsViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.heading
        holder.sale.text = "₹"+item.sale
        holder.mrp.text = "₹"+item.mrp
//        holder.discount.text = item.disc
        Picasso.get().load(item.image).into(holder.img)
        holder.constraint.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }
        holder.open.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDescriptionFragment(item.id))
        }

    }

}

class NewArrivalsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.newArrivalimg)
    val title: TextView = itemView.findViewById(R.id.newArrivalfirst)
    val mrp: TextView = itemView.findViewById(R.id.newArrivalThirdscad)
    val sale: TextView = itemView.findViewById(R.id.newArrivalsecond)
    val open: Button = itemView.findViewById(R.id.textView4comboas)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.newArrivalsConstraint)

//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}