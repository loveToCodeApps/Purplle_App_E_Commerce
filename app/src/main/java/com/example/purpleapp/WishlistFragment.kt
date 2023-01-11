package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentWishlistBinding
import org.json.JSONException
import org.json.JSONObject

class WishlistFragment : Fragment() {

lateinit var binding : FragmentWishlistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
 binding = DataBindingUtil.inflate(inflater,R.layout.fragment_wishlist,container,false)

        getWishlistData()


    return binding.root


    }

    private fun getWishlistData() {
        val myWishlist = mutableListOf<MyWishlistProductData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_WISH_LIST,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in (array.length()-1) downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = MyWishlistProductData(
                                objectArtist.optString("image"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading"),
                                objectArtist.optString("id"),
                                objectArtist.optString("desc_id")




                                )
                            myWishlist.add(banners)
                            val adapter = MyWishlistProductAdapter(myWishlist,requireActivity().applicationContext)
                            binding.prodRcv.adapter=adapter
                        }
                    } else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
//                    binding.textView13.visibility=View.VISIBLE
//                    binding.lottieAnimationView.visibility=View.VISIBLE
//                    binding.progressBar1.visibility=View.GONE

                }

            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString().trim()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)
    }

}