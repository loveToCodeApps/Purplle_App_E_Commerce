package com.example.purpleapp

import android.os.Bundle
import android.text.TextUtils
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
import com.example.purpleapp.databinding.FragmentEditAddressBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class EditAddressFragment : Fragment() {

lateinit var binding : FragmentEditAddressBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_address,container,false)

//        val activity:MainActivity = requireActivity() as MainActivity
//        activity.binding.bottomNavigationView.visibility = View.GONE

        val args = EditAddressFragmentArgs.fromBundle(requireArguments())
        binding.userFirstName.setText( args.address)
        binding.userShippingState.setText( args.state)
        binding.userShippingCity.setText( args.city)
        binding.userShippingZipcode.setText( args.zipcode)



        binding.button8.setOnClickListener {
            editAddress()
        }




    return binding.root

    }

    private fun editAddress() {
        var address = binding.userFirstName.text.toString()
        var state = binding.userShippingState.text.toString()
        var city = binding.userShippingCity.text.toString()
        var pincode = binding.userShippingZipcode.text.toString()
        var id =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()

        if (TextUtils.isEmpty(pincode)) {
            binding.userShippingZipcode.error = "Please enter your pincode"
            binding.userShippingZipcode.requestFocus()
        }

        if (TextUtils.isEmpty(city)) {
            binding.userShippingCity.error = "Please enter your city "
            binding.userShippingCity.requestFocus()
        }

        if (TextUtils.isEmpty(state)) {
            binding.userShippingState.error = "Please enter your state "
            binding.userShippingState.requestFocus()
        }

        if (TextUtils.isEmpty(address)) {
            binding.userFirstName.error = "Please enter your address"
            binding.userFirstName.requestFocus()
        }
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_EDIT_ADDRESS,
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
                params["user_id"] = id
                params["shipping_state"] = state
                params["shipping_city"] = city
                params["shipping_zipcode"] = pincode
                params["address"] = address + " , " + state + " , " + city + " , " + pincode
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)



    }


}