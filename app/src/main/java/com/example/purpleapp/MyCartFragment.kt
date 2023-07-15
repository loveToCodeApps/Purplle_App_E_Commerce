package com.example.purpleapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentMyCartBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class MyCartFragment : Fragment() {
    lateinit var binding:FragmentMyCartBinding
    var count = 0
    var total = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_cart, container, false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

        binding.button11.setOnClickListener {
            it.findNavController().navigate(MyCartFragmentDirections.actionMyCartFragmentToShipToAddressFragment(count.toString(),total,"shortbuy","0"))
        }
val i1 = Internet()
if (i1.checkConnection(requireContext()))
{
    getMyCartData()
    getMyCartTotalPrice()
    binding.animationView.visibility = View.GONE
    binding.textView89.visibility=View.VISIBLE
    binding.button11.visibility=View.VISIBLE
    binding.myCartProdList.visibility=View.VISIBLE
    binding.cardView2.visibility=View.VISIBLE
    binding.textView90.visibility=View.VISIBLE
}
else
{
    binding.animationView.visibility = View.VISIBLE
    binding.textView89.visibility=View.GONE
    binding.button11.visibility=View.GONE
    binding.myCartProdList.visibility=View.GONE
    binding.cardView2.visibility=View.GONE
    binding.textView90.visibility=View.GONE
    binding.animationViewEmpty.visibility=View.GONE
    binding.textView4.visibility=View.GONE
    binding.button16.visibility=View.GONE
    Snackbar.make(requireActivity().findViewById(android.R.id.content),
        "Poor internet connection!!", Snackbar.LENGTH_LONG).show();
}


        binding.button16.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }


        setHasOptionsMenu(true)
        return binding.root

    }

    private fun getMyCartTotalPrice() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_CART_TOTAL_PRICE,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")
                            val objectArtist = array.getJSONObject(0)
                        total = objectArtist.getString("sale")
                           binding.textView89.text = "₹"+ total

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    binding.animationViewEmpty.visibility=View.VISIBLE
                    binding.textView4.visibility=View.VISIBLE
                    binding.button16.visibility=View.VISIBLE
                    binding.button11.setEnabled(false)
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

    private fun getMyCartData() {
        val myCartProdList = mutableListOf<MyCartData>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_CART,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                         val array = obj.getJSONArray("user")
                        count = array.length()



                        //   for (i in (array.length()-1) until  1) {
                        for (i in array.length()-1 downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = MyCartData(
                                objectArtist.optString("image"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading"),
                                objectArtist.optString("sum"),
                                objectArtist.optString("id"),
                                objectArtist.optString("quantity"),
                                objectArtist.optString("disc")

                            )
                            myCartProdList.add(banners)
                            val adapter = MyCartAdapter(myCartProdList,requireActivity().applicationContext)
                            binding.myCartProdList.adapter = adapter
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
                    binding.animationViewEmpty.visibility=View.VISIBLE
                    binding.textView4.visibility=View.VISIBLE
                    binding.button16.visibility=View.VISIBLE
                    binding.button11.setEnabled(false)
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
        inflater.inflate(R.menu.clear_my_cart,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId)
       {
            R.id.clearCart-> {
                if (binding.animationViewEmpty.visibility == View.GONE) {
                    clearMyCart()
                }
            else
                {
                    Toast.makeText(requireContext(),"Your cart is already empty!!",Toast.LENGTH_SHORT).show()
                }
            }
                    else -> {
               return super.onOptionsItemSelected(item)
           }
       }

       return true
    }

    private fun clearMyCart() {
        val myCartlist = mutableListOf<MyCartData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_DELETE_ALL_MY_CART,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(requireActivity().applicationContext , obj.getString("message"), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.myCartFragment)

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



