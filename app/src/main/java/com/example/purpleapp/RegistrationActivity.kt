package com.example.purpleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.User
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.ActivityRegistrationBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        supportActionBar?.hide()

        binding.textView85.setOnClickListener {
            finish()
            startActivity(Intent(this,LoginActivity::class.java))
        }


        //if the user is already logged in we will directly start the MainActivity (profile) activity
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

        binding.button.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

        val firstname = binding.editTextTextPersonName.text.trim()
        val middlename = binding.editTextTextPersonName2.text.trim()
        val lastname = binding.editTextTextPersonName3.text.trim()
        val phone = binding.editTextTextPersonName4.text.trim()
        val email = binding.editTextTextPersonName5.text.trim()



        if (TextUtils.isEmpty(firstname)) {
            binding.editTextTextPersonName.error = "Please enter first name"
            binding.editTextTextPersonName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(middlename)) {
            binding.editTextTextPersonName2.error = "Please enter middle name"
            binding.editTextTextPersonName2.requestFocus()
            return
        }
        if (TextUtils.isEmpty(lastname)) {
            binding.editTextTextPersonName3.error = "Please enter last name"
            binding.editTextTextPersonName3.requestFocus()
            return
        }


        if (TextUtils.isEmpty(phone)) {
            binding.editTextTextPersonName4.error = "Please enter phone number"
            binding.editTextTextPersonName4.requestFocus()
            return
        }
        if (TextUtils.getTrimmedLength(phone) > 10 || TextUtils.getTrimmedLength(phone) < 10) {
            binding.editTextTextPersonName4.error = "enter 10 digit phone number"
            binding.editTextTextPersonName4.requestFocus()
            return
        }

        if (TextUtils.isEmpty(email)) {
            binding.editTextTextPersonName5.error = "Please enter your email"
            binding.editTextTextPersonName5.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextTextPersonName5.error = "Enter a valid email"
            binding.editTextTextPersonName5.requestFocus()
            return
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_REGISTER,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                        //starting the MainActivity activity
                        finish()
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["firstname"] = firstname.toString()
                params["middlename"] = middlename.toString()
                params["lastname"] = lastname.toString()
                params["email"] = email.toString()
                params["phone"] = phone.toString()
                return params
            }
        }

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)



    }
}

