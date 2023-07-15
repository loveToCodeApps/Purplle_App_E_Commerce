package com.example.purpleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.*
import com.example.purpleapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        supportActionBar?.hide()

        binding.textView79.setOnClickListener {
            finish()
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.textView139.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.imageView6.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }



        // check if already logged in or not
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

        binding.button2.setOnClickListener {
            var i1 = Internet()
            if(i1.checkConnection(this))
            {
                //userLogin()
                sendotp()
            }
            else
            {
                Toast.makeText(this,"Please check internet connectivity",Toast.LENGTH_SHORT).show()
            }



        }


        binding.button20.setOnClickListener {
            var i1 = Internet()
            if(i1.checkConnection(this))
            {
                //userLogin()
                verifyotp()
            }
            else
            {
                Toast.makeText(this,"Please check internet connectivity",Toast.LENGTH_SHORT).show()
            }



        }

        binding.textView142.setOnClickListener {
            binding.tl2.visibility = View.INVISIBLE
            binding.tl1.visibility = View.VISIBLE
            binding.button20.visibility = View.INVISIBLE
            binding.button2.visibility = View.VISIBLE
            binding.textView142.visibility = View.INVISIBLE

        }
    }


    private fun verifyotp() {

        val phone = binding.textView140.text.toString()
        val otp = binding.otp.text.toString()


        if (TextUtils.isEmpty(otp)) {
            binding.otp.error = "otp can't be empty!!"
            binding.otp.requestFocus()
            return
        }
        if (TextUtils.getTrimmedLength(otp) < 5) {
            binding.otp.error = "enter 5 digit otp!!"
            binding.otp.requestFocus()
            return
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_VERIFY_OTP,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()

                        userLogin()


                    } else {
                        Toast.makeText(
                            applicationContext,
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
                    applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["phone_no"] =phone
                params["otp"] = otp

                return params
            }
        }

        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun sendotp() {


        val phone = binding.phoneBox.text.toString()
        binding.textView140.text = phone

        if (TextUtils.isEmpty(phone)) {
            binding.phoneBox.error = "Please enter phone number"
            binding.phoneBox.requestFocus()
            return
        }

        if (TextUtils.getTrimmedLength(phone) < 10) {
            binding.phoneBox.error = "enter 10 digit phone number"
            binding.phoneBox.requestFocus()
            return
        }


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_SEND_OTP,
            Response.Listener { response ->

                try {
                    //converting response to json object

                    val obj = JSONObject(response)

                    //if no error in response
                    if (obj.getBoolean("error")) {

                        Toast.makeText(
                           applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                    else {
                        Toast.makeText(
                           applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.tl2.visibility = View.VISIBLE
                        binding.tl1.visibility = View.INVISIBLE
                        binding.button20.visibility = View.VISIBLE
                        binding.button2.visibility = View.INVISIBLE
                        binding.textView142.visibility = View.VISIBLE

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                   applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["phone_no"] = binding.phoneBox.text.toString()

                return params
            }
        }

        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun userLogin() {
        val number = binding.textView140.text.toString()
        var address: String
        var state: String
        var city: String
        var pincode: String

//        if (TextUtils.isEmpty(number)) {
//            binding.phoneBox.error = "Please enter your phone number"
//            binding.phoneBox.requestFocus()
//            return
//        }
//        if (TextUtils.getTrimmedLength(number) >= 1 && TextUtils.getTrimmedLength(number) <= 9) {
//            binding.phoneBox.error = "please enter 10 digits"
//            binding.phoneBox.requestFocus()
//            return
//        }

        //if everything is fine
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_LOGIN,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
//
                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")
//                        var address =  userJson.getString("address")
                        if (userJson.getString("address") == null || userJson.getString("address") == "null") {
                            address = "Not available"
                        } else {
                            address = userJson.getString("address")
                        }
//----------------------------------------------------------------------------
                        if (userJson.getString("state") == null || userJson.getString("state") == "null" || userJson.getString("state").length==0) {
                            state = "Not available"
                        }
                        else
                        {
                            state = userJson.getString("state")
                        }

//------------------------------------------------------------------------------------
                        if (userJson.getString("city") == null || userJson.getString("city") == "null") {
                            city = "Not available"
                        } else {
                            city = userJson.getString("city")
                        }
//-------------------------------------------------------------------------------------
                        if (userJson.getString("zipcode") == null || userJson.getString("zipcode") == "null") {
                            pincode = "Not available"
                        } else {
                            pincode = userJson.getString("zipcode")
                        }
                        //--------------------------------------------------------------------------------

                        //creating a new user object
                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("firstname"),
                            userJson.getString("middlename"),
                            userJson.getString("lastname"),
                            userJson.getString("email"),
                            userJson.getString("phone"),
                            address,
                           state,
                            city,
                            pincode,
                            userJson.getString("shipping_whatsappno"),
                            userJson.getString("billing_whatsappno"),
                            userJson.getString("shipping_city"),
                            userJson.getString("shipping_state"),
                            userJson.getString("shipping_zipcode")

                            )

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(applicationContext).userLogin(user)
                        //starting the MainActivity
                        finish()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["phone"] = number
                return params
            }
        }

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)

    }
}



