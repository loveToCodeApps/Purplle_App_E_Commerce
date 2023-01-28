package com.example.purpleapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.example.purpleapp.api.User
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentEditProfileBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var birthDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        val activity: MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

        binding.userFirstName.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName.toString())
        binding.userLastName.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.lastName.toString())
        binding.userEmail.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.email.toString())
binding.userShippingWhatsappNumber.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.shipping_whatsappno)
        binding.userShippingBillingNumber.setText(SharedPrefManager.getInstance(requireActivity().applicationContext).user.billing_whatsappno)
        binding.button8.setOnClickListener {
            editMyProfile()

            // Logout after editing the profile


        }
        return binding.root

    }


    private fun editMyProfile() {

        val first = binding.userFirstName.text
        val last = binding.userLastName.text
        val email = binding.userEmail.text
        var id =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
        val shipping_state = binding.userShippingState.text
        val shipping_city = binding.userShippingCity.text
        val shipping_zipcode = binding.userShippingZipcode.text
        val shipping_whatsappno = binding.userShippingWhatsappNumber.text
        val billing_whatsappno = binding.userShippingBillingNumber.text




        if (TextUtils.isEmpty(first)) {
            binding.userFirstName.error = "Please enter your first name"
            binding.userFirstName.requestFocus()
            return
        }
        if (TextUtils.isEmpty(last)) {
            binding.userLastName.error = "Please enter your last name"
            binding.userLastName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(email)) {
            binding.userEmail.error = "Please enter your email"
            binding.userEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.userEmail.error = "Enter a valid email"
            binding.userEmail.requestFocus()
            return
        }


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_EDIT_PROFILE,
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

                        if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
                            SharedPrefManager.getInstance(requireActivity().applicationContext)
                                .logout()
                            requireActivity().finish()
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
                params["first_name"] = first.toString()
                params["last_name"] = last.toString()
                params["full_name"] = first.toString() + " " + last.toString()
                params["email"] = email.toString()
                params["user_id"] = id
                params["shipping_state"] = shipping_state.toString()
                params["shipping_city"] = shipping_city.toString()
                params["shipping_zipcode"] = shipping_zipcode.toString()
                params["shipping_whatsappno"] = shipping_whatsappno.toString()
                params["billing_whatsappno"] = billing_whatsappno.toString()

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


}