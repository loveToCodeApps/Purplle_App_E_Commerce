package com.example.purpleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentBrandBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class BrandFragment : Fragment() {

    lateinit var binding:FragmentBrandBinding
    var searchList =  arrayListOf<BrandNameData>()
    lateinit var adapter : BrandNameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_brand, container, false)



        val i1 = Internet()
        if (i1.checkConnection(requireContext()))
        {
            //get all brands
            getBrands()
            binding.animationView.visibility = View.GONE
            binding.brandNameList.visibility = View.VISIBLE
            binding.searchBrands.visibility = View.VISIBLE
        }
        else
        {

            binding.animationView.visibility = View.VISIBLE
            binding.brandNameList.visibility = View.GONE
            binding.searchBrands.visibility = View.GONE
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG).show();
        }

       binding.searchBrands.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               activitiesFilter(newText)
                return true
            }
        })

        return binding.root
    }

    private fun activitiesFilter(newText: String?) {
        Log.i("@@@@@@@@@@@@@@@@@@@@","$newText")
        var newFilteredList = arrayListOf<BrandNameData>()

        for (i in searchList)
        {
            if (i.brand.contains(newText!!.uppercase()) || i.brand.contains(newText!!.lowercase())){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
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

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BrandNameData(
                                objectArtist.getString("heading")
                            )

                            brandProductList.add(banners)
                             adapter = BrandNameAdapter(brandProductList)
                            searchList = brandProductList as ArrayList<BrandNameData>
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





