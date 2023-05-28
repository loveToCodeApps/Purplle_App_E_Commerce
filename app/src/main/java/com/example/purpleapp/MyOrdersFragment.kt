package com.example.purpleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentMyOrdersBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyOrdersFragment : Fragment() {


    lateinit var binding : FragmentMyOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
 binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_my_orders,container,false)


//        val activity:MainActivity = requireActivity() as MainActivity
//        activity.binding.bottomNavigationView.visibility = View.GONE

        val i1 = Internet()
        if (i1.checkConnection(requireContext()))
        {
            getMyOrders()
            binding.animationView.visibility = View.GONE
//            val myOrdersDataList = mutableListOf<MyOrdersData>()
    //       binding.myOrdersList.adapter = MyOrdersAdapter(myOrdersDataList)

        }
        else
        {
            binding.animationView.visibility = View.VISIBLE
            binding.myOrdersList.visibility = View.GONE
            binding.animationViewEmpty.visibility=View.GONE
            binding.textView4.visibility=View.GONE
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG).show();
        }
        return binding.root

    }

    private fun getMyOrders() {
        val myOrdersDataList = mutableListOf<MyOrdersData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_ORDERS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        //   for (i in (array.length()-1) until  1) {
                        for (i in (array.length()-1) downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = MyOrdersData(
                                objectArtist.optString("order_no"),
                                objectArtist.optString("total_price"),
                                objectArtist.optString("created_on"),
                                objectArtist.optString("status")

                                )
                            myOrdersDataList.add(banners)
                            val adapter = MyOrdersAdapter(myOrdersDataList)
                            binding.myOrdersList.adapter = adapter


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
                    binding.animationViewEmpty.visibility=View.VISIBLE
                    binding.textView4.visibility=View.VISIBLE

                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    requireActivity().applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
                        .trim()
                return params

            }
        }
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)

    }
}