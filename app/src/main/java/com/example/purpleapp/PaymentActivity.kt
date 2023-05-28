package com.example.purpleapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat


class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    lateinit var binding: ActivityPaymentBinding
    lateinit var total: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)

        binding.textView91.text = SharedPrefManager.getInstance(applicationContext).user.address.toString()
        binding.textView124.text = SharedPrefManager.getInstance(applicationContext).user.firstName + " " +
                SharedPrefManager.getInstance(applicationContext).user.middleName + " " + SharedPrefManager.getInstance(applicationContext).user.lastName
        binding.textView126.text = SharedPrefManager.getInstance(applicationContext).user.phone.toString()


        getMyCurrentOrder()
        startPayment()


        binding.button19.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getMyCurrentOrder() {
        val myOrdersDataList = mutableListOf<MyOrdersData>()
        val stringRequest = @SuppressLint("SuspiciousIndentation")
        object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_CURRENT_ORDER,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")
                        val objectArtist = array.getJSONObject(0)
                        total = objectArtist.optString("total_price")
                        binding.textView113.text = objectArtist.optString("order_no")
                        binding.textView37.text = objectArtist.optString("total_price")
                        var date = objectArtist.optString("created_on")
                        val parser = SimpleDateFormat("yyyy-MM-ddHH:mm:ss")
                        val formatter = SimpleDateFormat("dd.MM.yyyy")
                        binding.textView114.text = formatter.format(parser.parse(date))

                    } else {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
//                    binding.animationViewEmpty.visibility=View.VISIBLE
//                    binding.textView4.visibility=View.VISIBLE

                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] =
                    SharedPrefManager.getInstance(applicationContext).user.id.toString()
                        .trim()
                return params

            }
        }
        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)

    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()
        val etApiKey = "rzp_test_LQWOeFsCp7jmQ6"
        co.setKeyID(etApiKey)

        try {

            val intent = intent
            val tot = intent.getStringExtra("total")
            var amt = tot.toString()
            var options = JSONObject()
            options.put("name", "Affetta Mart")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.drawable.affetta_logo_new)
            options.put("currency", "INR")
            options.put("amount", Math.round(amt.toFloat() * 100).toInt())
            options.put("send_sms_hash", true);

            val prefill = JSONObject()
            prefill.put("email", SharedPrefManager.getInstance(applicationContext).user.email)
            prefill.put("contact", SharedPrefManager.getInstance(applicationContext).user.phone)

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        try {
            Toast.makeText(this, "Payment is successful", Toast.LENGTH_SHORT).show();
            getShipmentData()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed due to some error", Toast.LENGTH_SHORT).show();
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
                            binding.shipmentList.adapter = adapter

                            //   binding.textView89.text = banners.myCartTotalPrice

                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
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
                    applicationContext,
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

        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}








