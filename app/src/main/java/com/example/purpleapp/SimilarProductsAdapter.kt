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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
        if (item.mrp==item.sale)
        {
            holder.sale.visibility = View.VISIBLE
            holder.sale.text = "₹"+item.sale
            holder.mrp.visibility = View.GONE
            holder.disc.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        else
        {

            holder.mrp.text = "₹"+item.mrp
            holder.sale.text = "₹"+item.sale
            holder.disc.text = (item.disc).take(2)+"%off"
        }


//        holder.discount.text = item.disc
      //  Picasso.get().load(item.image1).into(holder.img)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(holder.img.context).load(item.image1).thumbnail(0.05f)
            .apply(requestOptions).into(holder.img)

        Glide.get(holder.img.context).clearMemory()

        Thread(Runnable {
            // This method must be called on a background thread.
            Glide.get(holder.img.context).clearDiskCache()
        }).start()





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
    val mrp:TextView = itemView.findViewById(R.id.newArrivalThirdscad)
    val sale:TextView = itemView.findViewById(R.id.salesimilrs)
    val openProd:Button = itemView.findViewById(R.id.textView4com)
    val disc:TextView = itemView.findViewById(R.id.discountOfSimilar)
    val constraint:ConstraintLayout = itemView.findViewById(R.id.constraintBrandsProd)
    val line:View = itemView.findViewById(R.id.view4)




    //  val sale:TextView = itemView.findViewById(R.id.categprodsecond)
//    val discount:TextView = itemView.findViewById(R.id.offerProdFourth)

}