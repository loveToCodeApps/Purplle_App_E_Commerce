package com.example.purpleapp

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentSearchedProductsBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class SearchedProductsFragment : Fragment() {

    lateinit var binding : FragmentSearchedProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_searched_products,container,false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE


        val args = SearchedProductsFragmentArgs.fromBundle(requireArguments())
        binding.textView137.text = "'"+args.search+"'"

        getSearchedProducts()

    return binding.root

    }

    private fun getSearchedProducts() {
        val brandsList = mutableListOf<ViewAllProductsData>()
        val args = SearchedProductsFragmentArgs.fromBundle(requireArguments())
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_HOME_SEARCHED_PRODUCTS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length()>0) {
                            binding.textView133.visibility = View.VISIBLE
                            binding.textView137.visibility = View.VISIBLE
                            binding.searchedList.visibility = View.VISIBLE
                            binding.textView42.visibility = View.GONE
                            binding.animationViewNotAvailable.visibility = View.GONE


                            //   for (i in (array.length()-1) until  1) {
                            for (i in 0..array.length()-1) {
                                val objectArtist = array.getJSONObject(i)
                                val banners = ViewAllProductsData(
                                    objectArtist.optString("heading"),
                                    objectArtist.optString("sale"),
                                    objectArtist.optString("mrp"),
                                    objectArtist.optString("image"),
                                    objectArtist.optString("id"),
                                    objectArtist.optString("name")
                                )
                                brandsList.add(banners)
                                val adapter = SearchedProductsAdapter(brandsList)
                                binding.searchedList.adapter = adapter
                            }
                        }

                    } else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    binding.textView133.visibility = View.GONE
                            binding.textView137.visibility = View.GONE
                            binding.searchedList.visibility = View.GONE
                            binding.textView42.visibility = View.VISIBLE
                            binding.animationViewNotAvailable.visibility = View.VISIBLE
                }

            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["category"] = args.search
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)

    }


}