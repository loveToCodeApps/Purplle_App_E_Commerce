package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentBrandBinding
import org.json.JSONException
import org.json.JSONObject

class BrandFragment : Fragment() {
  
lateinit var binding:FragmentBrandBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     binding = DataBindingUtil.inflate(inflater,R.layout.fragment_brand,container,false)


        getBrands()
        val brandNameList = mutableListOf<BrandNameData>()
//        brandNameList.add(BrandNameData("All Brands"))
//        brandNameList.add(BrandNameData("   A      Albion  "))
//        brandNameList.add(BrandNameData("          Anastasia"))
//        brandNameList.add(BrandNameData("          Aqua"))
//        brandNameList.add(BrandNameData("   B      Balmshell"))

       binding.brandNameList.adapter = BrandNameAdapter(brandNameList)
    
    
    return binding.root
    }

    private fun getBrands() {
        val brandProductList = mutableListOf<BrandNameData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BRAND_NAMES_ALL,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 1 .. array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BrandNameData(
                                objectArtist.getString("heading")
                            )

                            brandProductList.add(banners)
                            val adapter = BrandNameAdapter(brandProductList)
                            binding.brandNameList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }


}