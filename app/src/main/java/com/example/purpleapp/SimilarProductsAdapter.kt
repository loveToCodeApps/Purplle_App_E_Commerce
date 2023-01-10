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

class SimilarProductsAdapter(val data: MutableList<BrandAllProductData>):Adapter<SimilarProductsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.similar_products_item_view,parent,false)
        return SimilarProductsViewHolder(view)
    }



    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: SimilarProductsViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.heading
        // holder.sale.text = "₹"+item.sale
        holder.mrp.text = "₹"+item.sale
        holder.sale.text = "₹"+item.mrp

//        holder.discount.text = item.disc
        Picasso.get().load(item.image1).into(holder.img)




        holder.openProd.setOnClickListener {
            it.findNavController().navigate(ProductDescriptionFragmentDirections.actionProductDescriptionFragmentSelf(item.id))

        }


        holder.constraint.setOnClickListener {
            it.findNavController().navigate(ProductDescriptionFragmentDirections.actionProductDescriptionFragmentSelf(item.id))

        }

    }


}

class SimilarProductsViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.brandsprodimg)
    val title:TextView = itemView.findViewById(R.id.brandsprodfirst)
    val mrp:TextView = itemView.findViewById(R.id.brandsprodsecond)
    val sale:TextView = itemView.findViewById(R.id.newArrivalThirdscad)
    val openProd:Button = itemView.findViewById(R.id.textView4com)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.constraintBrandsProd)



    //  val sale:TextView = itemView.findViewById(R.id.categprodsecond)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}