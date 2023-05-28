package com.example.purpleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class ShipToAddressProductAdapter(val data : MutableList<ShipToAddressProductsData>):Adapter<ShipToAddressProductHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipToAddressProductHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ship_to_address_product_item_view,parent,false)
        return ShipToAddressProductHolder(view)
    }

    override fun onBindViewHolder(holder: ShipToAddressProductHolder, position: Int) {
        val item = data[position]
        var quantity = 1

      //  Picasso.get().load(item.myCartProdImg).into(holder.imgs)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(holder.imgs.context).load(item.myCartProdImg).thumbnail(0.05f)
            .apply(requestOptions).into(holder.imgs)

        Glide.get(holder.imgs.context).clearMemory()

        Thread(Runnable {
            // This method must be called on a background thread.
            Glide.get(holder.imgs.context).clearDiskCache()
        }).start()



        holder.prices.text="₹"+item.myCartProdNewPrice+"  |"
        holder.Qtys.text = "Qty:"+" "+item.qty.toString()
        holder.headings.text=item.myCartProdHeading

    }


    override fun getItemCount() = data.size



}

class ShipToAddressProductHolder(itemView: View):ViewHolder(itemView)
{
    val imgs: ImageView = itemView.findViewById(R.id.imageView200)
    val headings: TextView = itemView.findViewById(R.id.textView200)
    val prices: TextView =itemView.findViewById(R.id.textView201)
    val Qtys: TextView =itemView.findViewById(R.id.textView202)
}





