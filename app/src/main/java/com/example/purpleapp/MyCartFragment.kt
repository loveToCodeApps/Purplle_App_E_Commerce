package com.example.purpleapp

import android.os.Bundle
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
import com.example.purpleapp.databinding.FragmentMyCartBinding
import org.json.JSONException
import org.json.JSONObject

class MyCartFragment : Fragment() {
lateinit var binding : FragmentMyCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
 binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_cart,container,false)

      //  val myCartDataList = mutableListOf<MyCartData>()
//        myCartDataList.add(MyCartData(R.drawable.offer_prod_two,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
//        myCartDataList.add(MyCartData(R.drawable.offer_prod_one,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
//        myCartDataList.add(MyCartData(R.drawable.offer_prod_four,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
//        myCartDataList.add(MyCartData(R.drawable.offer_prod_three,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
//        myCartDataList.add(MyCartData(R.drawable.offer_prod_five,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
//        myCartDataList.add(MyCartData(R.drawable.offer_prod_two,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))

      //  binding.myCartProdList.adapter = MyCartAdapter(myCartDataList)



        getMyCartData()
    return binding.root

    }

    private fun getMyCartData() {
        val myCartProdList = mutableListOf<MyCartData>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_CART,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")



//                        if(array.length()<=0)
//                        {
//                            binding.textView13.visibility=View.VISIBLE
//                            binding.lottieAnimationView.visibility=View.VISIBLE
//                        }
//                        else
//                        {
//                            binding.textView13.visibility=View.INVISIBLE
//                            binding.lottieAnimationView.visibility=View.INVISIBLE
//                            binding.progressBar1.visibility=View.GONE
//
//                        }





                        for (i in (array.length()-1) downTo 0) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = MyCartData(
                                objectArtist.optString("image"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("heading")
                            )
                            myCartProdList.add(banners)
                            val adapter = MyCartAdapter(myCartProdList)
                            binding.myCartProdList.adapter=adapter
                        }
                    } else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
//                    binding.textView13.visibility=View.VISIBLE
//                    binding.lottieAnimationView.visibility=View.VISIBLE
//                    binding.progressBar1.visibility=View.GONE

                }

            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString().trim()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)




    }


}



