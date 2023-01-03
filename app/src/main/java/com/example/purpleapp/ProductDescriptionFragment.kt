package com.example.purpleapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.User
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentProductDescriptionBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class ProductDescriptionFragment : Fragment() {
lateinit var binding : FragmentProductDescriptionBinding
// lateinit var userId:String
lateinit var prod_id:String
    lateinit var prod_desc_id:String
    lateinit var prod_im:String
    lateinit var prod_head:String
    lateinit var prod_ogp:String
    lateinit var prod_newp:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding   = DataBindingUtil.inflate(inflater,R.layout.fragment_product_description,container,false)

//        if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn)
//        {
//            userId = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
//        }


    getProductDescriptionDetails()

//       prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_one))
//        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_two))
//        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_three))
//        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_four))



        val smallProdImgList = mutableListOf<SmallProductData>()
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_one))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_two))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_three))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_four))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_five))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_one))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_two))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_three))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_four))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_five))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_one))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_two))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_three))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_four))
        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_five))


        binding.smallprod.adapter = SmallProductAdapter(smallProdImgList)

binding.addToCartBtn.setOnClickListener {
  if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
      addToCart()
  }
    else{
        Toast.makeText(requireContext(),"only Logged In users can add to cart !!",Toast.LENGTH_SHORT).show()
      startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}


        binding.wishlistBtn.setOnClickListener {
            if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
                addToWishlist()
            }
            else{
                Toast.makeText(requireContext(),"only Logged In users can add to wish list !!",Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }


        setHasOptionsMenu(true)
        return binding.root

    }

    private fun addToWishlist() {
        val args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())

        val userId = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_TO_WISH_LIST,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),obj.getString("message")
                            , Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),obj.getString("message")
                            , Snackbar.LENGTH_LONG).show();                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["prod_id"] = prod_id
                params["user_id"] = userId
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)


    }

    private fun getProductDescriptionDetails() {
        var args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
        var product_id = args.prodId
        val prodDescriptionImgList = mutableListOf<ProductImageData>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PRODUCT_DETAILED_DESCRIPTION,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                      //  Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")

                        binding.textView9.text = userJson.getString("heading")
                        binding.textView10.text = "₹" + userJson.getString("sale")
                        binding.textView11.text = userJson.getString("mrp")



                        prodDescriptionImgList.add(ProductImageData(userJson.getString("image1")))
                        prodDescriptionImgList.add(ProductImageData(userJson.getString("image2")))
                        prodDescriptionImgList.add(ProductImageData(userJson.getString("image3")))
                        prodDescriptionImgList.add(ProductImageData(userJson.getString("image4")))
                        prodDescriptionImgList.add(ProductImageData(userJson.getString("image5")))
                        prodDescriptionImgList.add(ProductImageData(userJson.getString("image6")))


                        prod_id = userJson.getString("id")
                        prod_desc_id = userJson.getString("prod_desc_id")
                        prod_im = userJson.getString("image1")
                        prod_head = userJson.getString("heading")
                        prod_ogp = userJson.getString("mrp")
                        prod_newp = userJson.getString("sale")


                        binding.prodImgList.adapter = ProductDescriptionAdapter(prodDescriptionImgList)

                    } else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                    error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = product_id

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)
    }

    private fun addToCart() {

    val args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())

        val userId = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_TO_CART,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["prod_id"] = prod_id
                params["prod_desc_id"] = prod_desc_id
                params["user_id"] = userId
                params["unit_price"] = prod_newp
                params["total_price"] = (prod_newp.toInt() * 1).toString()
                params["confirm_mobile"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.phone

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_description_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      when(item.itemId)
      {
          R.id.shareProduct->   startActivity(getShareIntent())
      }
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    // Creating our Share Intent
    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT,"https://www.afeta.com/prod")
        return shareIntent
    }
}