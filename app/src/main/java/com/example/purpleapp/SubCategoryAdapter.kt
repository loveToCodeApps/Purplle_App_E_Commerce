package com.example.purpleapp


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class SubCategoryAdapter(val data : List<SubCategoryData>,var primary:String,var categ:String,var cont:Activity):Adapter<SubCategoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sub_category_item_view,parent,false)
        return SubCategoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val item = data[position]


            holder.title.text = item.heading

        holder.ogLayout.setOnClickListener {
            getChildCategoryCount(it,item.id,categ,primary,item.heading)

        }

    }

    private fun getChildCategoryCount(item:View,id:String,cat:String,prim:String,heading:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_CHILD_CATEGORY_COUNT,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        val count = obj.getString("count")
                        if (count=="0")
                        {
                            item.findNavController().navigate(SubCategoryFragmentDirections.actionSubCategoryFragmentToCategoryAllProductsFragment(primary,categ,heading,""))
                        }
                        else
                        {
                            item.findNavController().navigate(SubCategoryFragmentDirections.actionSubCategoryFragmentToChildCategoryFragment(
                                id,categ,primary,heading))
                        }

                    } else {
                        Toast.makeText(
                            cont.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    cont.applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["sub_id"] = item.id.toString()
                return params

            }
        }

        VolleySingleton.getInstance(cont.applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun getItemCount() = data.size

}

class SubCategoryViewHolder(itemView: android.view.View):ViewHolder(itemView)
{

    val title:TextView = itemView.findViewById(R.id.serviceCategoryTitle)
    val ogLayout:ConstraintLayout = itemView.findViewById(R.id.ogLayout)
}



