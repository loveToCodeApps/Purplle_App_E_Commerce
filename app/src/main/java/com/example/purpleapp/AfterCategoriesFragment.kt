package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentAfterCategoriesBinding
import com.example.purpleapp.databinding.FragmentCategoryBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject


class AfterCategoriesFragment : Fragment() {

    lateinit var binding: FragmentAfterCategoriesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_after_categories, container, false)


        //set Action Bar Title
        var args = AfterCategoriesFragmentArgs.fromBundle(requireArguments())
        (activity as AppCompatActivity).supportActionBar?.title =  args.category


        val i1 = Internet()
        if (i1.checkConnection(requireContext()))
        {
            //Main category list on page
            getAfterCategoryList()
            binding.afterCategoryList.visibility = View.VISIBLE
            binding.animationView.visibility=View.GONE
        }
        else
        {
            binding.animationView.visibility=View.VISIBLE
            binding.afterCategoryList.visibility = View.GONE
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG).show();
        }

        return binding.root
    }

    private fun getAfterCategoryList() {
        val afterCategoryList = mutableListOf<ServiceCategoryData>()
        var args = AfterCategoriesFragmentArgs.fromBundle(requireArguments())


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_AFTER_CATEGORIES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ServiceCategoryData(
                                objectArtist.getString("url"),
                                objectArtist.getString("heading"),
                                objectArtist.getString("img_name"),
                                objectArtist.getString("id")

                                // here i have given default value of false to the expandable layout
                            )

                            afterCategoryList.add(banners)
                            val adapter = ServiceCategoryAdapter(afterCategoryList,"AfterCategoryFragment",args.category)
                            binding.afterCategoryList.adapter = adapter
                        }

                    }
                    else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    binding.animationViewNotAvailable.visibility = View.VISIBLE
                    binding.textView42.visibility = View.VISIBLE
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
                params["category"] = args.category
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


}