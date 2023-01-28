package com.example.purpleapp

import android.os.Bundle
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
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentViewOrderBinding
import org.json.JSONException
import org.json.JSONObject

class ViewOrderFragment : Fragment() {

    lateinit var binding : FragmentViewOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_order,container,false)


        var args = ViewOrderFragmentArgs.fromBundle(requireArguments())
        binding.textView113.text = args.orderId
        binding.textView114.text = args.orderDate
        binding.textView37.text = args.orderTotal

        getShipmentData()


    return binding.root

    }


    private fun getShipmentData() {
        val myCartProdList = mutableListOf<ShipToAddressProductsData>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_SHIPMENT_DATA,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")



                        //   for (i in (array.length()-1) until  1) {
                        for (i in array.length()-1 downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ShipToAddressProductsData(
                                objectArtist.optString("image"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading"),
                                objectArtist.optString("sum"),
                                objectArtist.optString("id"),
                                objectArtist.optString("quantity")
                            )
                            myCartProdList.add(banners)
                            val adapter = ShipToAddressProductAdapter(myCartProdList)
                            binding.shipmentList.adapter = adapter

                            //   binding.textView89.text = banners.myCartTotalPrice

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
//                    binding.animationViewEmpty.visibility=View.VISIBLE
//                    binding.textView4.visibility=View.VISIBLE
//                    binding.button16.visibility=View.VISIBLE
//                    binding.button11.setEnabled(false)
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
                params["id"] = binding.textView113.text.toString()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

}