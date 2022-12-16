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
import com.example.purpleapp.databinding.FragmentCategoryBinding
import org.json.JSONException
import org.json.JSONObject

class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding
     var serviceSubCategoryList = mutableListOf<ServiceSubCategoryData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)

        //Main category list on page
        getServiceCategoryList()
        getServiceSubCategoryList()

//        val serviceCategoryList = mutableListOf<ServiceCategoryData>()
//        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_one,"MakeUp V"))
//        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_three,"SkinCare V"))
//        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_two,"HairCare V"))
//        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_five,"Men V"))
//        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_four,"Fragrance V"))


//        binding.serviceCategoryList.adapter = ServiceCategoryAdapter(serviceCategoryList)

        return binding.root

    }

    private fun getServiceSubCategoryList() {

        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_SUB_CATEGORIES,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ServiceSubCategoryData(
                                objectArtist.getString("heading")
                                // here i have given default value of false to the expandable layout
                            )

                            serviceSubCategoryList.add(banners)
                        //   // val adapter = ServiceSubCategoryAdapter(serviceSubCategoryList)
                         //   binding.

                          //      .adapter = adapter
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
        requestQueue.add(stringRequest)    }

    private fun getServiceCategoryList() {
        val serviceCategoryList = mutableListOf<ServiceCategoryData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_CATEGORIES,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ServiceCategoryData(
                                objectArtist.getString("url"),
                                objectArtist.getString("heading")
                                // here i have given default value of false to the expandable layout
                            )

                            serviceCategoryList.add(banners)
                            val adapter = ServiceCategoryAdapter(serviceCategoryList,serviceSubCategoryList)
                            binding.serviceCategoryList.adapter = adapter
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


