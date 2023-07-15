package com.example.purpleapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentShipToAddressBinding
import com.github.chrisbanes.photoview.PhotoView
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat


class ShipToAddressFragment : Fragment(), PaymentResultListener {

    lateinit var binding: FragmentShipToAddressBinding
    lateinit var dialog: Dialog
    lateinit var order_no: String
    lateinit var order_created_on: String
    lateinit var total_price: String
    lateinit var address: String
    lateinit var state: String
    lateinit var city: String
    lateinit var zipcode: String

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ship_to_address, container, false)


        // payment mode drop down
        val roles = resources.getStringArray(R.array.roles)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, roles)
        // Here don't use binding.autocompletetv.adapter = adapter
        //it will give u error , instead use setAdapter()
        binding.editTextTextPersonName10.setAdapter(arrayAdapter)



        address = ""
        state = ""
        city = ""
        zipcode = ""

        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.address != "Not available") {
            address =
                SharedPrefManager.getInstance(requireActivity().applicationContext).user.address
        }



        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.state != "Not available") {
            state = SharedPrefManager.getInstance(requireActivity().applicationContext).user.state
        }


        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.city != "Not available") {
            city = SharedPrefManager.getInstance(requireActivity().applicationContext).user.city
        }



        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.zipcode != "Not available") {
            zipcode =
                SharedPrefManager.getInstance(requireActivity().applicationContext).user.zipcode
        }



        binding.textView91.text =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.address.toString()
        binding.textView124.text =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName + " " +
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.middleName + " " + SharedPrefManager.getInstance(
                requireActivity().applicationContext
            ).user.lastName
        binding.textView126.text =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.phone.toString()


        // get address if it is already present
//        if(binding.textView93.text == "EDIT/CHANGE ADDRESS")
//        {
//            getMyAddress()
//        }
        //------------------------------------------
        var args = ShipToAddressFragmentArgs.fromBundle(requireArguments())

        if(args.orderStatus=="shortbuy") {
            getMyCartData()
        }
        else if (args.orderStatus=="longbuy")
        {
            getBuyNowData()
        }

        val activity: MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE


        Log.i("111", SharedPrefManager.getInstance(requireActivity().applicationContext).user.email)


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


        var ok: Button = dialog.findViewById(R.id.ok)
        ok.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "You are welcome!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_shipToAddressFragment_to_myOrdersFragment)

        }


        binding.button12.setOnClickListener {
            //uncomment below 2 functions after razorpay process gets done
            //    dialog.show()

            if (binding.editTextTextPersonName10.text.toString().length != 0) {
                insertBillDetails(binding.editTextTextPersonName10.text.toString())
            } else {
                Toast.makeText(requireContext(), "Please select payment mode", Toast.LENGTH_SHORT)
                    .show()
            }
            var args = ShipToAddressFragmentArgs.fromBundle(requireArguments())
            // val intent = Intent(requireContext(), PaymentActivity::class.java)
//            intent.putExtra("total", args.total);
//            startActivity(intent)
//            requireActivity().finish()
            //startPayment()
        }

        binding.textView124.text =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName + " " + SharedPrefManager.getInstance(
                requireActivity().applicationContext
            ).user.lastName

        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.address == "Not available" || SharedPrefManager.getInstance(
                requireActivity().applicationContext
            ).user.address == "null"
        ) {
            binding.textView91.text = "( you need to add a proper address for delivery of product )"
            binding.textView91.setTextColor(R.color.purple_700)
            binding.textView93.text = "ADD NEW ADDRESS"
            binding.button12.setEnabled(false)
            binding.button12.setText("Add shipping adress")
        } else {
            binding.textView91.text =
                SharedPrefManager.getInstance(requireActivity().applicationContext).user.address.toString()
            binding.textView91.setTextColor(R.color.black)
            binding.textView93.text = "UPDATE/CHANGE ADDRESS"
            binding.button12.setEnabled(true)

        }

        binding.textView93.setOnClickListener {
            it.findNavController().navigate(
                ShipToAddressFragmentDirections.actionShipToAddressFragmentToEditAddressFragment(
                    address,
                    state,
                    city,
                    zipcode
                )
            )
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

        return binding.root

    }

    private fun getBuyNowData() {
        val myCartProdList = mutableListOf<ShipToAddressProductsData>()

        var args = ShipToAddressFragmentArgs.fromBundle(requireArguments())

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PRODUCT_DETAILED_DESCRIPTION,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("user")


                        //   for (i in (array.length()-1) until  1) {
                        for (i in array.length() - 1 downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ShipToAddressProductsData(
                                objectArtist.optString("image1"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("id"),
                                "1"
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
                params["id"] = args.prodId

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }



//    private fun getMyAddress() {
//        val stringRequest = object : StringRequest(
//            Request.Method.POST, URLs.URL_GET_MY_ADDRESS,
//            Response.Listener { response ->
//
//                try {
//                    val obj = JSONObject(response)
//                    if (!obj.getBoolean("error")) {
//                        val array = obj.getJSONArray("user")
//
//                        val userJson = obj.getJSONObject("user")
//
//                        address = userJson.getString("picture")
//                        state = userJson.getString("picture")
//                        city = userJson.getString("picture")
//                        zipcode = userJson.getString("picture")
//
//                            //    objectArtist.optString("image")
//
//
//                    } else {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//
//                    }
//
//                } catch (e: JSONException) {
//                    e.printStackTrace()
////                    binding.animationViewEmpty.visibility=View.VISIBLE
////                    binding.textView4.visibility=View.VISIBLE
////                    binding.button16.visibility=View.VISIBLE
////                    binding.button11.setEnabled(false)
//                }
//
//            },
//            Response.ErrorListener { error ->
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        ) {
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params["id"] =
//                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
//                        .trim()
//                return params
//
//            }
//        }
//
//        VolleySingleton.getInstance(requireActivity().applicationContext)
//            .addToRequestQueue(stringRequest)
//    }

    private fun insertBillDetails(payStatus: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_INSERT_BILL_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        var args = ShipToAddressFragmentArgs.fromBundle(requireArguments())
                        val intent = Intent(requireContext(), PaymentActivity::class.java)
                        intent.putExtra("total", args.total);
                        intent.putExtra(
                            "payStatus",
                            binding.editTextTextPersonName10.text.toString()
                        )
                        startActivity(intent)
                        requireActivity().finish()
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
                val args = ShipToAddressFragmentArgs.fromBundle(requireArguments())

                val params = HashMap<String, String>()
                params["total"] = args.total
                params["name"] =
                    SharedPrefManager.getInstance(requireContext()).user.firstName + " " + SharedPrefManager.getInstance(
                        requireContext()
                    ).user.middleName + " " + SharedPrefManager.getInstance(requireContext()).user.lastName
                params["address"] = SharedPrefManager.getInstance(requireContext()).user.address
                params["state"] = SharedPrefManager.getInstance(requireContext()).user.city
                params["city"] = SharedPrefManager.getInstance(requireContext()).user.state
                params["pincode"] = SharedPrefManager.getInstance(requireContext()).user.zipcode
                params["contact"] = SharedPrefManager.getInstance(requireContext()).user.phone
                params["email_id"] = SharedPrefManager.getInstance(requireContext()).user.email
                params["id"] = SharedPrefManager.getInstance(requireContext()).user.id.toString()
                params["quantity"] = args.count

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)


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
                params["id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()

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
                        for (i in array.length() - 1 downTo 0) {
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

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(requireContext(), "Payment is successful", Toast.LENGTH_SHORT).show();
        //getMyCurrentOrder()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(requireContext(), "Payment Failed due to error", Toast.LENGTH_SHORT).show();

    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = requireActivity()
        val co = Checkout()
        val etApiKey = "rzp_test_LQWOeFsCp7jmQ6"
        co.setKeyID(etApiKey)

        try {
            var args = ShipToAddressFragmentArgs.fromBundle(requireArguments())
            var amt = args.total
            var options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", Math.round(amt.toFloat() * 100).toInt())
            options.put("send_sms_hash", true);

            val prefill = JSONObject()
            prefill.put(
                "email",
                SharedPrefManager.getInstance(requireActivity().applicationContext).user.email
            )
            prefill.put(
                "contact",
                SharedPrefManager.getInstance(requireActivity().applicationContext).user.phone
            )

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    //
}
