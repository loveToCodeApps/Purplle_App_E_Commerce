package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class CategoryHomeAllProductsAdapter(val data: MutableList<CategoryHomeAllProductsData>):Adapter<CategoryHomeAllProductsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHomeAllProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_homepage_all_products_item_view,parent,false)
        return CategoryHomeAllProductsViewHolder(view)
    }

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: CategoryHomeAllProductsViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.heading
        if (item.mrp==item.sale)
        {
            holder.mrp.visibility = View.VISIBLE
            holder.mrp.text = "₹"+item.sale
            holder.sale.visibility = View.GONE
            holder.disc.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        else
        {
            holder.mrp.text = "₹"+item.sale
            holder.sale.text = "₹"+item.mrp
            holder.disc.text = (item.disc)+"%off"

        }



        // holder.sale.text = "₹"+item.sale

//        holder.discount.text = item.disc
        Picasso.get().load(item.image1).into(holder.img)

        holder.constraint.setOnClickListener {
            it.findNavController().navigate(CategoryHomeAllProductsFragmentDirections.actionCategoryHomeAllProductsFragmentToProductDescriptionFragment(item.id))

        }
        holder.openProd.setOnClickListener {
            it.findNavController().navigate(CategoryHomeAllProductsFragmentDirections.actionCategoryHomeAllProductsFragmentToProductDescriptionFragment(item.id))

        }

    }


}

class CategoryHomeAllProductsViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.categoHomeprodimg)
    val title:TextView = itemView.findViewById(R.id.categoHomeprodfirst)
    val mrp:TextView = itemView.findViewById(R.id.categoHomeprodsecond)
    val sale:TextView = itemView.findViewById(R.id.newArrivalThirdscad)
    val disc:TextView = itemView.findViewById(R.id.myDiscount)
    val line:View = itemView.findViewById(R.id.view4)
    val openProd:Button = itemView.findViewById(R.id.textView4comboas)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.categoryHomeConstraint)



    //  val sale:TextView = itemView.findViewById(R.id.categprodsecond)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}