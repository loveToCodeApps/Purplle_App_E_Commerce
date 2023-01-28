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
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class MyCartAdapter(val data : List<MyCartData> , var context: Context):Adapter<MyCartViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.my_cart_product_item_view,parent,false)
        return MyCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val item = data[position]
        var quantity = 1
        Picasso.get().load(item.myCartProdImg).into(holder.img)
        holder.price.text="₹"+item.myCartProdNewPrice
        holder.Qty.text = item.qty.toString()
        holder.cutPrice.text=item.myCartProdOrgPrice
        holder.heading.text=item.myCartProdHeading


        // button to delete a item
        holder.DeleteItem.setOnClickListener {
            val myCartlist = mutableListOf<MyCartData>()
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_DELETE_FROM_MY_CART,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(context , obj.getString("message"), Toast.LENGTH_SHORT).show()
                            it.findNavController().navigate(R.id.myCartFragment)

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
                    params["id"] = item.myCartProdId
                    params["userid"] = SharedPrefManager.getInstance(context).user.id.toString()

                    return params

                }
            }

            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)
          //  it.findNavController().navigate(R.id.homeFragment)
           // Toast.makeText(it.context,"Deleted item successfully!!",Toast.LENGTH_SHORT).show()
        }


        //plus button
        holder.plusBtn.setOnClickListener {
            if (quantity >= 0) {
                holder.updateBtn.visibility = View.VISIBLE
                quantity = quantity + 1
                holder.Qty.text = quantity.toString()

            }
        }

        //minus button
        holder.minusBtn.setOnClickListener {
          if(quantity>1) {
              holder.updateBtn.visibility = View.VISIBLE
              quantity = quantity - 1
              holder.Qty.text = quantity.toString()
          }
          }

        holder.updateBtn.setOnClickListener {
          //  Toast.makeText(it.context,"Cart updated successfully",Toast.LENGTH_SHORT).show()


            val myCartProdList = mutableListOf<MyCartData>()
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_UPDATE_MY_CART_QUANTITY,
                Response.Listener { response ->

                    try {
                        val obj = JSONObject(response)
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(
                                context,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                            it.findNavController().navigate(R.id.myCartFragment)


                        }
                        else {
                            Toast.makeText(
                               context,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
//                    binding.textView13.visibility=View.VISIBLE
//                    binding.lottieAnimationView.visibility=View.VISIBLE
//                    binding.progressBar1.visibility=View.GONE

                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        context,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["user_id"] =
                        SharedPrefManager.getInstance(context).user.id.toString()
                    params["prod_id"] = item.myCartProdId
                    params["number_of_items"] = holder.Qty.text.toString()
                    params["unit_price"] = item.myCartProdNewPrice

                    return params

                }
            }

            VolleySingleton.getInstance(context)
                .addToRequestQueue(stringRequest)

          //  it.visibility = View.GONE
           // it.findNavController().navigate(R.id.homeFragment)

        }


    }


    override fun getItemCount() = data.size



}

class MyCartViewHolder(itemView: View):ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.imageView39)
    val heading: TextView = itemView.findViewById(R.id.textView96)
    val price: TextView =itemView.findViewById(R.id.textView97)
    val cutPrice: TextView =itemView.findViewById(R.id.textView104)
    val plusBtn: Button =itemView.findViewById(R.id.button13)
    val minusBtn: Button =itemView.findViewById(R.id.button15)
    val updateBtn: Button =itemView.findViewById(R.id.button5)
    val Qty: TextView =itemView.findViewById(R.id.textView23)
    val DeleteItem:ImageView=itemView.findViewById(R.id.imageView42)

//    val total_price: TextView =itemView.findViewById(R.id.textView89)





    // val discount: TextView =itemView.findViewById(R.id.textView)
  //  val rate : Button =itemView.findViewById(R.id.button14)
   // val reviews: TextView =itemView.findViewById(R.id.textView102)
  //  val likes: TextView =itemView.findViewById(R.id.textView100)

}