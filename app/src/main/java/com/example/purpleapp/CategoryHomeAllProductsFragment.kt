package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentCategoryHomeAllProductsBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class CategoryHomeAllProductsFragment : Fragment() {

    lateinit var binding : FragmentCategoryHomeAllProductsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_home_all_products,container,false)
        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

        getParticularCategoryProducts()

        return binding.root

    }

    private fun getParticularCategoryProducts() {
        val categoryList = mutableListOf<CategoryHomeAllProductsData>()

        val args = CategoryHomeAllProductsFragmentArgs.fromBundle(requireArguments())
        val category = args.categoryName
        binding.textView129.text = category

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_CATEGORY_PRODUCTS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        //   for (i in (array.length()-1) until  1) {

                        if (array.length()>0) {
                            binding.textView129.visibility = View.VISIBLE
                            binding.textView132.visibility = View.VISIBLE
                            binding.categoryHomeAllProductsList.visibility=View.VISIBLE
                            binding.animationViewNotAvailable.visibility = View.GONE
                            binding.textView42.visibility = View.GONE

                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val banners = CategoryHomeAllProductsData(
                                    objectArtist.optString("heading"),
                                    objectArtist.optString("sale"),
                                    objectArtist.optString("mrp"),
                                    objectArtist.optString("image"),
                                    objectArtist.optString("id")
                                )
                                categoryList.add(banners)
                                val adapter = CategoryHomeAllProductsAdapter(categoryList)
                                binding.categoryHomeAllProductsList.adapter = adapter
                            }
                        }
                        else if (array.length()<=0)
                        {
                            binding.textView129.visibility = View.GONE
                            binding.textView132.visibility = View.GONE
                            binding.categoryHomeAllProductsList.visibility=View.GONE
                            binding.animationViewNotAvailable.visibility = View.VISIBLE
                            binding.textView42.visibility = View.VISIBLE

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
                params["category"] = category
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)



    }


}