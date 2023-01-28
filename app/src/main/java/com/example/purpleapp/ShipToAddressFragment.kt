package com.example.purpleapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentShipToAddressBinding
import org.json.JSONException
import org.json.JSONObject


class ShipToAddressFragment : Fragment() {

lateinit var  binding: FragmentShipToAddressBinding
lateinit var dialog : Dialog

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ship_to_address,container,false)
        getMyCartData()

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE



//        dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.order_dialog)
//        dialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog_background)

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.order_dialog)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window!!.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_dialog_background))
        }
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )




        dialog.setCancelable(false) //Optional

        dialog.window!!.attributes.windowAnimations =
            R.style.DialogAnimation //Setting the animations to dialog



        var ok:Button = dialog.findViewById(R.id.ok)
        ok.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),"You are welcome!!",Toast.LENGTH_SHORT).show()
findNavController().navigate(R.id.action_shipToAddressFragment_to_myOrdersFragment)

        }


binding.button12.setOnClickListener {
   insertBillDetails()
   // confirmOrder()
    dialog.show()
}

        binding.textView124.text = SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName+" "+SharedPrefManager.getInstance(requireActivity().applicationContext).user.lastName

        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.address=="Not available" || SharedPrefManager.getInstance(requireActivity().applicationContext).user.address=="null")
        {
            binding.textView91.text = "( you need to add a proper address for delivery of product )"
           binding.textView91.setTextColor(R.color.purple_700)
            binding.textView93.text = "ADD NEW ADDRESS"
            binding.button12.setEnabled(false)
        }
        else
        {
            binding.textView91.text = SharedPrefManager.getInstance(requireActivity().applicationContext).user.address.toString()
            binding.textView91.setTextColor(R.color.black)
            binding.textView93.text = "UPDATE/CHANGE ADDRESS"
            binding.button12.setEnabled(true)
        }

        binding.textView93.setOnClickListener {
            it.findNavController().navigate(R.id.action_shipToAddressFragment_to_editAddressFragment)
        }




//
//        val prodList = mutableListOf<ShipToAddressProductsData>()
//
//        prodList.add(ShipToAddressProductsData("Natural skin glow","https://cdn.shopify.com/s/files/1/1375/4957/products/FCVitaminC_1024x1024.jpg?v=1667893691"))
//        prodList.add(ShipToAddressProductsData("Natural skin glow","https://cdn.shopify.com/s/files/1/1375/4957/products/FCVitaminC_1024x1024.jpg?v=1667893691"))
//        prodList.add(ShipToAddressProductsData("Natural skin glow","https://cdn.shopify.com/s/files/1/1375/4957/products/FCVitaminC_1024x1024.jpg?v=1667893691"))


//        val adapter = ShipToAddressProductsAdapter(prodList)
//        binding.recyclerView.adapter = adapter



        // edit address
//binding.textView93.setOnClickListener {
//    it.findNavController().navigate(R.id.editAddressFragment)
//}

    return  binding.root

    }

    private fun insertBillDetails() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_INSERT_BILL_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val args = ShipToAddressFragmentArgs.fromBundle(requireArguments())

                val params = HashMap<String, String>()
                params["total"] = args.total
                params["name"] = SharedPrefManager.getInstance(requireContext()).user.firstName+" "+SharedPrefManager.getInstance(requireContext()).user.middleName+" "+SharedPrefManager.getInstance(requireContext()).user.lastName
                params["address"] = SharedPrefManager.getInstance(requireContext()).user.address
                params["state"] = SharedPrefManager.getInstance(requireContext()).user.city
                params["city"] = SharedPrefManager.getInstance(requireContext()).user.state
                params["pincode"] = SharedPrefManager.getInstance(requireContext()).user.zipcode
                params["contact"] = SharedPrefManager.getInstance(requireContext()).user.phone
                params["email_id"] =SharedPrefManager.getInstance(requireContext()).user.email
                params["id"] =SharedPrefManager.getInstance(requireContext()).user.id.toString()
                params["quantity"] = args.count

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)



    }

    private fun confirmOrder() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_CONFIRM_ORDER,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
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
                val params = java.util.HashMap<String, String>()
                params["id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getMyCartData() {
        val myCartProdList = mutableListOf<ShipToAddressProductsData>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_CART,
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
                            binding.orderProdList.adapter = adapter

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




