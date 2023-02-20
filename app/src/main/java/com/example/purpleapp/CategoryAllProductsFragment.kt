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
import com.example.purpleapp.api.SharedPrefManager
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
        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE


        val args = CategoryAllProductsFragmentArgs.fromBundle(requireArguments())
        val category = args.categName
        binding.textView137CategName.text= category



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
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

//
                        if (array.length()>0) {
                            binding.textView133.visibility = View.VISIBLE
                            binding.textView137CategName.visibility = View.VISIBLE
                            binding.categoryAllProductsList.visibility = View.VISIBLE
                            binding.animationViewNotAvailable.visibility = View.GONE
                            binding.textView42.visibility = View.GONE
                            //   for (i in (array.length()-1) until  1) {
                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val banners = CategoryAllProductsData(
                                    objectArtist.optString("heading"),
                                    objectArtist.optString("sale"),
                                    objectArtist.optString("mrp"),
                                    objectArtist.optString("image"),
                                    objectArtist.optString("id"),
                                    objectArtist.optString("disc")
                                )
                                categorList.add(banners)
                                val adapter = CategoryAllProductsAdapter(categorList)
                                binding.categoryAllProductsList.adapter = adapter
                            }
                        }
                    } else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    binding.textView133.visibility = View.GONE
                            binding.textView137CategName.visibility = View.GONE
                            binding.categoryAllProductsList.visibility = View.GONE
                            binding.animationViewNotAvailable.visibility = View.VISIBLE
                            binding.textView42.visibility = View.VISIBLE
                }

            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }
        ) {
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