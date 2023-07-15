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
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentBrandAllProductsBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class BrandAllProductsFragment : Fragment() {

lateinit var binding : FragmentBrandAllProductsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_brand_all_products,container,false)
//        val activity:MainActivity = requireActivity() as MainActivity
//        activity.binding.bottomNavigationView.visibility = View.GONE

        getParticularBrandProducts()


        return  binding.root


    }

    private fun getParticularBrandProducts() {
        val brandsList = mutableListOf<BrandAllProductData>()

        val args = BrandAllProductsFragmentArgs.fromBundle(requireArguments())
        val brand = args.brandName
        binding.textView130.text = brand

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_BRAND_PRODUCTS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        binding.totalprod.text = "total " + array.length().toString() +" results"


                        if (array.length()>0)
                        {
                            binding.brandsAllProductsList.visibility = View.VISIBLE
                            binding.textView130.visibility = View.VISIBLE
                        binding.textView131.visibility = View.VISIBLE
                        binding.animationViewNotAvailable.visibility = View.GONE
                            binding.textView42.visibility = View.GONE

                            //   for (i in (array.length()-1) until  1) {
                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BrandAllProductData(
                                objectArtist.optString("heading"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("image"),
                                objectArtist.optString("id"),
                                objectArtist.optString("name"),
                                objectArtist.optString("disc")

                            )
                            brandsList.add(banners)
                            val adapter = BrandAllProductAdapter(brandsList)
                            binding.brandsAllProductsList.adapter = adapter
                        }

                        }
                        else if (array.length()<=0)
                        {
                            binding.brandsAllProductsList.visibility = View.GONE
                            binding.textView130.visibility = View.GONE
                            binding.textView131.visibility = View.GONE
                            binding.textView42.visibility = View.VISIBLE
                            binding.animationViewNotAvailable.visibility = View.VISIBLE
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
                params["brand"] = brand
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)



    }


}