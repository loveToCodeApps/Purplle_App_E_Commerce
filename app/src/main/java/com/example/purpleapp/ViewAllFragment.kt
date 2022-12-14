package com.example.purpleapp

import android.os.Bundle
import android.util.Log
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
import com.example.purpleapp.databinding.FragmentViewAllBinding
import org.json.JSONException
import org.json.JSONObject

class ViewAllFragment : Fragment() {

lateinit var binding:FragmentViewAllBinding
var listType=" "
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

    binding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_all,container,false)
       var args = ViewAllFragmentArgs.fromBundle(requireArguments())
            listType = args.typeOfList.toString()


       if (listType=="comboOffers")
       {
           getComboOffers()
           binding.textView137.text = "Combo Offers"
       }
        else if (listType=="featured")
       {
         getFeaturedProducts()
           binding.textView137.text = "Featured Products"

       }
       else if (listType=="newArrivals")
       {
           getNewArrivals()
           binding.textView137.text = "New Arrivals"

       }else if (listType=="hotDeals")
       {
            getHotDeals()
           binding.textView137.text = "Hot Deals"

       }



        return binding.root

    }

    private fun getHotDeals() {
        val dealsList = mutableListOf<ViewAllProductsData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_HOT_DEALS_ALL,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ViewAllProductsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id")
                            )
                            dealsList.add(banners)
                            val adapter = ViewAllProductsAdapter(dealsList)
                            binding.typeOfList.adapter = adapter
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

    private fun getNewArrivals() {
        val newArrivalsList = mutableListOf<ViewAllProductsData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_NEW_ARRIVALS_ALL,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ViewAllProductsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id")
                            )
                            newArrivalsList.add(banners)
                            val adapter = ViewAllProductsAdapter(newArrivalsList)
                            binding.typeOfList.adapter = adapter
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

    private fun getFeaturedProducts() {
        val offerProductList = mutableListOf<ViewAllProductsData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_FEATURED_PRODUCTS_ALL,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0.. array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ViewAllProductsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id")
                            )

                            offerProductList.add(banners)
                            val adapter = ViewAllProductsAdapter(offerProductList)
                            binding.typeOfList.adapter = adapter
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

    private fun getComboOffers() {
        val comboOffersList = mutableListOf<ViewAllProductsData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_COMBO_OFFERS_ALL,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ViewAllProductsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id")


                            )
                            comboOffersList.add(banners)
                            val adapter = ViewAllProductsAdapter(comboOffersList)
                            binding.typeOfList.adapter = adapter
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