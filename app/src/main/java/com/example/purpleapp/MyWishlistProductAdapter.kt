package com.example.purpleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class MyWishlistProductAdapter (val data : List<MyWishlistProductData> , var context: Context):Adapter<MyWishlistProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWishlistProductViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val view = inflater.inflate(R.layout.my_wishlist_product_item_view,parent,false)
       return MyWishlistProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyWishlistProductViewHolder, position: Int) {


        val item = data[position]
        Picasso.get().load(item.wishlistProdImg).into(holder.img)
        holder.price.text="₹"+item.wishlistProdPrice
        holder.cutPrice.text="₹"+item.wislistProdCutPrice
        holder.heading.text=item.wishlistProdHeading
//        holder.discount.text=item.wishlistProdDiscount
//        holder.rate.text=item.wishlistProdRating
//        holder.reviews.text=item.wishlistProdReviewNo
//        holder.likes.text=item.wishlistProdLikes

        holder.addToCart.setOnClickListener {
            val userId = SharedPrefManager.getInstance(context).user.id.toString()
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_ADD_TO_CART_FROM_WISHLIST,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["prod_id"] = item.idOfProd
                    params["prod_desc_id"] = item.idOfProdDescription
                    params["user_id"] = userId
                    params["unit_price"] = item.wishlistProdPrice
                    params["total_price"] = item.wishlistProdPrice
                    params["confirm_mobile"] = SharedPrefManager.getInstance(context).user.phone
                    params["number_of_items"] = "1"


                    return params
                }
            }

            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)
            it.findNavController().navigate(R.id.myCartFragment)

        }
        holder.DeleteItem.setOnClickListener {
            val myWishlist = mutableListOf<MyWishlistProductData>()
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_DELETE_FROM_MY_WISH_LIST,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("user")



                        } else {
                            Toast.makeText(context , obj.getString("message"), Toast.LENGTH_SHORT).show()


                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
//                    binding.textView13.visibility=View.VISIBLE
//                    binding.lottieAnimationView.visibility=View.VISIBLE
//                    binding.progressBar1.visibility=View.GONE

                    }

                },
                Response.ErrorListener { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["id"] = item.idOfProd
                    params["userid"] = SharedPrefManager.getInstance(context).user.id.toString()

                    return params

                }
            }

            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)

            it.findNavController().navigate(R.id.wishlistFragment)
        }
    }


    override fun getItemCount() = data.size


}


class MyWishlistProductViewHolder(itemView: View):ViewHolder(itemView)
{
   val img:ImageView = itemView.findViewById(R.id.imageView30)
   val heading:TextView = itemView.findViewById(R.id.textView71)
   val price:TextView=itemView.findViewById(R.id.textView78)
   val cutPrice:TextView=itemView.findViewById(R.id.textView81)
    val addToCart:TextView=itemView.findViewById(R.id.textView84)
    val DeleteItem:TextView=itemView.findViewById(R.id.textView)

//       val discount:TextView=itemView.findViewById(R.id.textView82)
//        val rate : Button=itemView.findViewById(R.id.button9)
//            val reviews:TextView =itemView.findViewById(R.id.textView86)
//                val likes:TextView=itemView.findViewById(R.id.textView83)

}