package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        // holder.sale.text = "₹"+item.sale
        holder.mrp.text = "₹"+item.mrp
//        holder.discount.text = item.disc
        Picasso.get().load(item.image1).into(holder.img)




        holder.openProd.setOnClickListener {
            it.findNavController().navigate(CategoryHomeAllProductsFragmentDirections.actionCategoryHomeAllProductsFragmentToProductDescriptionFragment(item.id))

        }    }


}

class CategoryHomeAllProductsViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.categoHomeprodimg)
    val title:TextView = itemView.findViewById(R.id.categoHomeprodfirst)
    val mrp:TextView = itemView.findViewById(R.id.categoHomeprodsecond)
    val openProd:TextView = itemView.findViewById(R.id.openHomeProduct)

    //  val sale:TextView = itemView.findViewById(R.id.categprodsecond)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}