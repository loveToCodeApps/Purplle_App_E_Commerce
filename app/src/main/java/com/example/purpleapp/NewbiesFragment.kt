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
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentNewbiesBinding
import com.example.purpleapp.databinding.FragmentOffersBinding
import org.json.JSONException
import org.json.JSONObject

class NewbiesFragment : Fragment() {

    lateinit var binding : FragmentNewbiesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_newbies,container,false)

        val i1 = Internet()
        if (i1.checkConnection(requireContext()))
        {
            getNewArrivals()
            binding.animationView.visibility = View.GONE
            binding.textView133.visibility = View.VISIBLE
            binding.textView137.visibility = View.VISIBLE
            binding.typeOfList.visibility = View.VISIBLE
        }
        else
        {
            binding.animationView.visibility = View.VISIBLE
            binding.textView133.visibility = View.GONE
            binding.textView137.visibility = View.GONE
            binding.typeOfList.visibility = View.GONE
        }


return binding.root

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

                        binding.totalprod.text = "total " + array.length().toString() +" results"


                        for (i in 0 .. array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ViewAllProductsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id"),
                                objectArtist.getString("name"),
                                objectArtist.getString("disc")
                                )
                            newArrivalsList.add(banners)
                            val adapter = NewbiesAdapter(newArrivalsList)
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