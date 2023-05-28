package com.example.purpleapp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.test.core.app.ApplicationProvider
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentMyProfileBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class MyProfileFragment : Fragment() {

    lateinit var binding: FragmentMyProfileBinding
    private val PICK_IMAGE_REQUEST = 71
    lateinit var selectedPicture: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)

        val i1 = Internet()
        if (i1.checkConnection(requireContext()))
        {
            binding.profileLayout.visibility = View.VISIBLE
            getProfilePicture()
            binding.animationView.visibility = View.GONE
        }
        else
        {
            binding.profileLayout.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG).show();


        }







        binding.imageView23.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
        }

        binding.cardView5.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_myOrdersFragment5)
        }
        binding.cardView13.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_wishlistFragment)
        }


        binding.textView75.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_myProfileFragment_to_customerSupportFragment)
        }



        binding.textView69.text =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName + " " +
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.lastName



        binding.textView77.text =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.email

        binding.imageView24.setOnClickListener {
            binding.button17.visibility = View.VISIBLE
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
           // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
           // Log.i("@@@",intent.data.toString())
        }

        // Logout button code
        binding.button7.setOnClickListener {
            Toast.makeText(requireContext(), "Logout Successfully !!", Toast.LENGTH_SHORT).show()
//            Snackbar.make(
//                requireActivity().findViewById(android.R.id.content),
//                "Logout Successfully !!",
//                Snackbar.LENGTH_SHORT
//            ).show()
            SharedPrefManager.getInstance(requireActivity().applicationContext).logout()

            requireActivity().finish()
        }


        binding.button17.setOnClickListener {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_UPDATE_PROFILE_PICTURE,
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

                            it.findNavController().navigate(R.id.action_myProfileFragment_self)

//                            if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
//                                SharedPrefManager.getInstance(requireActivity().applicationContext)
//                                    .logout()
//                                requireActivity().finish()
                            // }
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
                    params["id"] =
                        SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
                    params["picture"] = selectedPicture


                    return params
                }
            }

            stringRequest.retryPolicy =
                DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
            it.visibility = View.GONE
        }


        binding.textView74.setOnClickListener {
            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            selectorIntent.data = Uri.parse("mailto:")

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("support@affetta.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "feedback for Affetta App")
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Please share your experience of using affetta app here in detail , Thank you !!"
            )
            emailIntent.selector = selectorIntent
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }





        return binding.root

    }

    private fun getProfilePicture() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PROFILE_PICTURE,
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
//
                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")
                        //Glide.with(requireActivity().applicationContext).load( userJson.getString("picture")).into(binding.imageView21)
                   if (userJson.getString("picture") == null || userJson.getString("picture")=="" || userJson.getString("picture").length==0 || userJson.getString("picture") == "null" || userJson.getString("picture") == "NULL")
                   {
                       Glide.with(this)
                           .load(userJson.getString("picture"))
                           .diskCacheStrategy(DiskCacheStrategy.NONE)
                           .skipMemoryCache(true)
                           .into((binding.imageView21))


                       Glide.with(this)
                           .load(userJson.getString("picture"))
                           .diskCacheStrategy(DiskCacheStrategy.NONE)
                           .skipMemoryCache(true)
                           .into((binding.imageView8))

                   }
                        Glide.with(this)
                            .load(userJson.getString("picture"))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into((binding.imageView21))

                        Glide.with(this)
                            .load(userJson.getString("picture"))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into((binding.imageView8))
                        //Glide.with(requireActivity().applicationContext).load( userJson.getString("picture")).into(binding.imageView8)

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
                params["id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE_REQUEST && resultCode === RESULT_OK && data != null && data.data != null) {
            val filePath: Uri = data.data!!
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes = baos.toByteArray()
                selectedPicture =
                    android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
                val bytesImage: ByteArray =
                    android.util.Base64.decode(selectedPicture, android.util.Base64.DEFAULT)
                Glide.with(requireActivity().applicationContext)
                    .load(bytesImage)
                    .into(binding.imageView21);


            } catch (e: IOException) {
                Toast.makeText(
                    ApplicationProvider.getApplicationContext(),
                    "Error: " + e.message,
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }
}



