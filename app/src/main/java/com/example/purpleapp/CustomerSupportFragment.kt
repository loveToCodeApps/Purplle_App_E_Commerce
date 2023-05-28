package com.example.purpleapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentCustomerSupportBinding
import org.json.JSONException
import org.json.JSONObject

class CustomerSupportFragment : Fragment() {

lateinit var binding : FragmentCustomerSupportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     binding = DataBindingUtil.inflate(inflater,R.layout.fragment_customer_support,container,false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE


        if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn)
        {
            binding.cusName.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName + " " +SharedPrefManager.getInstance(requireActivity().applicationContext).user.lastName)
            binding.cusEmail.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.email)
            binding.cusPhone.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.phone)

        }

        binding.button18.setOnClickListener {
            sendMsg()
        }

    return binding.root


    }

    private fun sendMsg() {

        var name = binding.cusName.text.toString()
        var email = binding.cusEmail.text.toString()
        var phone = binding.cusPhone.text.toString()
        var msg = binding.cusMsg.text.toString()

        if (TextUtils.isEmpty(name)) {
            binding.cusName.error = "Please enter your name"
            binding.cusName.requestFocus()
            return
        }
        if (TextUtils.isEmpty(email)) {
            binding.cusEmail.error = "Please enter your email"
            binding.cusEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.cusEmail.error = "Enter a valid email"
            binding.cusEmail.requestFocus()
            return
        }

        if (TextUtils.isEmpty(phone)) {
            binding.cusPhone.error = "Please enter phone number"
            binding.cusPhone.requestFocus()
            return
        }

        if (TextUtils.getTrimmedLength(phone) < 10) {
            binding.cusPhone.error = "enter 10 digit phone number"
            binding.cusPhone.requestFocus()
            return
        }

        if (TextUtils.isEmpty(msg)) {
            binding.cusMsg.error = "Please enter your message"
            binding.cusMsg.requestFocus()
            return
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_CUSTOMER_SUPPORT,
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

                        findNavController().navigate(R.id.homeFragment)
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
                params["name"] = name
                params["email"] = email
                params["phone"] = phone
                params["message"] = msg

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)


    }

}