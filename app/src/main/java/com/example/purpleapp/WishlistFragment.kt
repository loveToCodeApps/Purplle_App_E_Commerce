package com.example.purpleapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentWishlistBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class WishlistFragment : Fragment() {

    lateinit var binding: FragmentWishlistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

        val i1 = Internet()
        if (i1.checkConnection(requireContext())) {
            binding.animationView.visibility = View.GONE
            getWishlistData()
            binding.prodRcv.visibility = View.VISIBLE

        } else {
            binding.animationView.visibility = View.VISIBLE
            binding.prodRcv.visibility = View.GONE
            binding.animationViewEmpty.visibility = View.GONE
            binding.textView4.visibility = View.GONE
            binding.button6.visibility = View.GONE
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG
            ).show();

        }

        binding.button6.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }


        setHasOptionsMenu(true)
        return binding.root


    }

    private fun getWishlistData() {
        val myWishlist = mutableListOf<MyWishlistProductData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_WISH_LIST,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in (array.length() - 1) downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = MyWishlistProductData(
                                objectArtist.optString("image"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading"),
                                objectArtist.optString("id"),
                                objectArtist.optString("desc_id"),
                                objectArtist.optString("disc")

                            )
                            myWishlist.add(banners)
                            val adapter = MyWishlistProductAdapter(
                                myWishlist,
                                requireActivity().applicationContext
                            )
                            binding.prodRcv.adapter = adapter
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
                    binding.animationViewEmpty.visibility = View.VISIBLE
                    binding.textView4.visibility = View.VISIBLE
                    binding.button6.visibility = View.VISIBLE
//                    binding.textView13.visibility=View.VISIBLE
//                    binding.lottieAnimationView.visibility=View.VISIBLE
//                    binding.progressBar1.visibility=View.GONE

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.clear_my_wishlist, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearWishlist -> {
                if (binding.animationViewEmpty.visibility == View.GONE) {
                    clearMyWishlist()
                }
                else
                {
                    Toast.makeText(requireContext(),"Your wishlist is already empty!!",Toast.LENGTH_SHORT).show()
                }
            }
                else -> {
                return super.onOptionsItemSelected(item)
            }
        }

        return true
    }

    private fun clearMyWishlist() {
        val myWishlist = mutableListOf<MyWishlistProductData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_DELETE_ALL_FROM_MY_WISH_LIST,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(requireActivity().applicationContext , obj.getString("message"), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.wishlistFragment)


                    } else {
                        Toast.makeText(requireActivity().applicationContext , obj.getString("message"), Toast.LENGTH_SHORT).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
//                    binding.textView13.visibility=View.VISIBLE
//                    binding.lottieAnimationView.visibility=View.VISIBLE
//                    binding.progressBar1.visibility=View.GONE

                }

            },
            Response.ErrorListener { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["userid"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)

    }
}

