package com.example.purpleapp

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentProductDescriptionBinding
import org.json.JSONException
import org.json.JSONObject

class SizeAdapter(var color:List<sizeData>,var binding: FragmentProductDescriptionBinding,var frag:ProductDescriptionFragment):Adapter<SizeViewHolder>() {

     val mDataset: List<sizeData> = color
    var clicked = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.size_item_view,parent,false)
        return SizeViewHolder(view)
    }


    override fun onBindViewHolder(holder:SizeViewHolder, position: Int) {

        val item = color[position]

        if (item.size != "null" && item.size.length != 0)
        {
            holder.size.text = item.size
        }

//        holder.circle.setOnClickListener {
//
//            Handler().postDelayed({
//                holder.img.setVisibility(View.GONE);
//                isClick = false;
//                notifyDataSetChanged();
//            }, 100)
//
//
//            binding.textView147.text = item.size
//            holder.img.setVisibility(View.VISIBLE);
//        }


        if (clicked) {
            holder.img.visibility = View.VISIBLE
        }
        else
        {
            holder.img.visibility = View.GONE
        }

        holder.circle.setOnClickListener {
            clicked = true
            notifyItemChanged(position);
            binding.textView147.text = item.size

            // Volley request to get selected size data

            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_GET_SELECTED_SIZE_DATA,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                           // Toast.makeText(it.context, obj.getString("message"), Toast.LENGTH_SHORT).show()
//
//                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")

                            binding.textView10.text = "₹"+userJson.getString("sale")
                            binding.textView11.text = "₹"+userJson.getString("mrp")
                            binding.textView12.text = userJson.getString("discount")+"%off"


                            frag.prod_newp = userJson.getString("sale")
                            frag.prod_id = userJson.getString("id")
                            frag.prod_desc_id = userJson.getString("prod_desc_id")






                            //  findNavController().navigate(R.id.dashBoard)

                        } else {
                            Toast.makeText(it.context, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> Toast.makeText(it.context, error.message, Toast.LENGTH_SHORT).show() }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["desc_id"] = item.desc_id
                    return params
                }
            }

            VolleySingleton.getInstance(it.context).addToRequestQueue(stringRequest)



            //-------------------------------------------
        }

    }


    override fun getItemCount():Int = color.size
}


class SizeViewHolder(itemView: View) : ViewHolder(itemView)
{
    val size:TextView = itemView.findViewById(R.id.textView144)
    val img:ImageView = itemView.findViewById(R.id.imageView12)
    val circle:ConstraintLayout = itemView.findViewById(R.id.roundborder)
}


