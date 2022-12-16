package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class CategoryAllProductsAdapter(val data: MutableList<CategoryAllProductsData>):Adapter<CategoryAllProductsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAllProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_all_products_item_view,parent,false)
        return CategoryAllProductsViewHolder(view)
    }



    override fun getItemCount()=data.size
    override fun onBindViewHolder(holder: CategoryAllProductsViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.heading
       // holder.sale.text = "₹"+item.sale
       holder.mrp.text = "₹"+item.mrp
//        holder.discount.text = item.disc
        Picasso.get().load(item.image1).into(holder.img)


        holder.img.setOnClickListener {
            it.findNavController().navigate(R.id.productDescriptionFragment)
        }
    }


}

class CategoryAllProductsViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.categprodimg)
    val title:TextView = itemView.findViewById(R.id.categprodfirst)
    val mrp:TextView = itemView.findViewById(R.id.categprodsecond)
  //  val sale:TextView = itemView.findViewById(R.id.categprodsecond)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}