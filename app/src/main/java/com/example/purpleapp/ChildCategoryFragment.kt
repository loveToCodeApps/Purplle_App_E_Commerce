package com.example.purpleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentChildCategoryBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class ChildCategoryFragment : Fragment() {

    lateinit var binding : FragmentChildCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_child_category,container,false)


        //set Action Bar Title
        var args = ChildCategoryFragmentArgs.fromBundle(requireArguments())
        (activity as AppCompatActivity).supportActionBar?.title =  args.category


        val i1 = Internet()
        if (i1.checkConnection(requireContext()))
        {
            //Main category list on page
            getChildCategoryList()
            binding.childCategoryList.visibility = View.VISIBLE
            binding.animationView.visibility=View.GONE
        }
        else
        {
            binding.animationView.visibility=View.VISIBLE
            binding.childCategoryList.visibility = View.GONE
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG).show();
        }

        return  binding.root
    }

    private fun getChildCategoryList() {
        val SubCategoryList = mutableListOf<SubCategoryData>()
        var args = ChildCategoryFragmentArgs.fromBundle(requireArguments())
        Log.i("*****",args.subId)

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_CHILD_CATEGORIES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0.. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = SubCategoryData(
                                objectArtist.getString("id"),
                                objectArtist.getString("heading")
                            )

                            SubCategoryList.add(banners)
                            val adapter = ChildCategoryAdapter(SubCategoryList,args.primaryCategory,args.category,args.subCategory)
                            binding.childCategoryList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    findNavController().navigate(ChildCategoryFragmentDirections.actionChildCategoryFragmentToCategoryAllProductsFragment(args.primaryCategory,args.category,args.subCategory,""))
                      //getSubCategoryList()
                //    Toast.makeText(requireContext(),"Some error occured",Toast.LENGTH_SHORT).show()
//                    binding.animationViewNotAvailable.visibility = View.VISIBLE
//                    binding.textView42.visibility = View.VISIBLE



                   // findNavController().navigate(ChildCategoryFragmentDirections.actionChildCategoryFragmentToCategoryAllProductsFragment(args.primaryCategory,args.category,args.subCategory,""))
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
                params["sub_id"] = args.subId
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


    private fun getSubCategoryList() {

        val SubCategoryList = mutableListOf<SubCategoryData>()
        var args = SubCategoryFragmentArgs.fromBundle(requireArguments())


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SUB_CATEGORIES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = SubCategoryData(
                                objectArtist.getString("id"),
                                objectArtist.getString("heading")
                            )

                            SubCategoryList.add(banners)
                            val adapter = SubCategoryAdapter(SubCategoryList,args.primaryCategory,args.category,requireActivity())
                            binding.childCategoryList.adapter = adapter
                        }
                    } else {
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