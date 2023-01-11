package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentMyCartBinding
import org.json.JSONException
import org.json.JSONObject

class MyCartFragment : Fragment() {
    lateinit var binding: FragmentMyCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_cart, container, false)

        binding.button11.setOnClickListener {
            it.findNavController().navigate(R.id.shipToAddressFragment)
        }

        getMyCartData()
        return binding.root

    }

    private fun getMyCartData() {
        val myCartProdList = mutableListOf<MyCartData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_CART,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

//

                        //   for (i in (array.length()-1) until  1) {
                        for (i in (array.length()-1) downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = MyCartData(
                                objectArtist.optString("image"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading"),
                                objectArtist.optString("sum"),
                                objectArtist.optString("id")
                            )
                            myCartProdList.add(banners)
                            val adapter = MyCartAdapter(myCartProdList,requireActivity().applicationContext)
                            binding.myCartProdList.adapter = adapter
                            //   binding.textView89.text = banners.myCartTotalPrice

                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
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
                    requireActivity().applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
                        .trim()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }
}



