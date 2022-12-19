package com.example.purpleapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentCategoryAllProductsBinding
import com.google.firebase.crashlytics.buildtools.reloc.afu.org.checkerframework.checker.units.UnitsTools.s
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class CategoryAllProductsFragment : Fragment() {
    lateinit var binding:FragmentCategoryAllProductsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_all_products,container,false)




        getParticularCategoryProducts()


    return  binding.root

    }

    private fun getParticularCategoryProducts() {
        val categorList = mutableListOf<CategoryAllProductsData>()

        val args = CategoryAllProductsFragmentArgs.fromBundle(requireArguments())
       val category = args.categName

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_CATEGORY_PRODUCTS,
            Response.Listener { response ->

                try {

                    val obj = JSONObject()
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = CategoryAllProductsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image")
                            )

                            categorList.add(banners)
                            val adapter = CategoryAllProductsAdapter(categorList)
                            binding.categoryAllProductsList.adapter = adapter
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
            Response.ErrorListener { error ->
                Toast.makeText(
                    requireActivity().applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["category"] = category

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)



    }


}