package com.example.purpleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
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
import com.example.purpleapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.textView79.setOnClickListener {
           finish()
            startActivity(Intent(this,RegistrationActivity::class.java))
        }

        binding.textView139.setOnClickListener {
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }


        // check if already logged in or not
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

       binding.button2.setOnClickListener {


           userLogin()

       }

    }

    private fun userLogin() {
        val number = binding.phoneBox.text.toString()

        if (TextUtils.isEmpty(number)) {
            binding.phoneBox.error = "Please enter your phone number"
            binding.phoneBox.requestFocus()
        }
        if (TextUtils.getTrimmedLength(number) >= 1 && TextUtils.getTrimmedLength(number) <= 9 ) {
            binding.phoneBox.error = "please enter 10 digits"
            binding.phoneBox.requestFocus()
        }

        //if everything is fine
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_LOGIN,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        Snackbar.make(findViewById(android.R.id.content),
                            obj.getString("message"),
                            Snackbar.LENGTH_SHORT
                        ).show()




                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")


                        //creating a new user object
                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("firstname"),
                            userJson.getString("middlename"),
                            userJson.getString("lastname"),
                            userJson.getString("email"),
                            userJson.getString("phone")

                        )

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(applicationContext).userLogin(user)
                        //starting the MainActivity
                        finish()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                    error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
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



