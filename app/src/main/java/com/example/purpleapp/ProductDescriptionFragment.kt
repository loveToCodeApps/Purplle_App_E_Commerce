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
import com.android.volley.toolbox.Volley
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
     var quantitiesCounter:Int = 1
    lateinit var categ:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding   = DataBindingUtil.inflate(inflater,R.layout.fragment_product_description,container,false)

       var  showMoreDescription = "false"


    getProductDescriptionDetails()
//    getSimilarProducts()

//       prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_one))
//        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_two))
//        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_three))
//        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_four))



        val smallProdImgList = mutableListOf<SmallProductData>()
//        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_one))
//        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_two))
//        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_three))
//        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_four))
//        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_five))
//        smallProdImgList.add(SmallProductData(R.drawable.offer_prod_one))





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

        // plus and minus button
        binding.button13.setOnClickListener {
           if (quantitiesCounter>=1) {
               quantitiesCounter += 1
               binding.textView23.text = quantitiesCounter.toString()
           }
           }
        binding.button15.setOnClickListener {
            if (quantitiesCounter>1) {
            quantitiesCounter-=1
            binding.textView23.text = quantitiesCounter.toString()
        }
        }



        binding.textView31.setOnClickListener {
                it.findNavController().navigate(ProductDescriptionFragmentDirections.actionProductDescriptionFragmentToCategoryAllProductsFragment(categ))
        }



        //textview to show more description of product , bydefault it only shows 3 lines of description
        binding.textView24.setOnClickListener {
            if (showMoreDescription.equals("false"))
            {
                binding.textView38.maxLines = 100
                binding.textView24.text = "Show less >"
                showMoreDescription="true"
            }
            else if (showMoreDescription.equals("true"))
            {
                binding.textView38.maxLines = 3
                binding.textView24.text = "Show more >"
                showMoreDescription="false"
            }

        }

        setHasOptionsMenu(true)
        return binding.root

    }

    private fun getSimilarProducts() {
        val brandsList = mutableListOf<BrandAllProductData>()
//        binding.22.text = brand
        categ = binding.textView22.text.toString()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SIMILAR_PRODUCTS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        //   for (i in (array.length()-1) until  1) {
                        for (i in 0 until array.length() + 1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BrandAllProductData(
                                objectArtist.optString("heading"),
                                objectArtist.optString("sale"),
                                objectArtist.optString("mrp"),
                                objectArtist.optString("image"),
                                objectArtist.optString("id")
                            )
                            brandsList.add(banners)
                            val adapter = SimilarProductsAdapter(brandsList)
                            binding.recyclerView2.adapter=adapter
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
                val params = java.util.HashMap<String, String>()
                params["category"] = binding.textView22.text.toString()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)

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
        val smallProdImgList = mutableListOf<SmallProductData>()

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
                        binding.textView11.text = "₹" +userJson.getString("mrp")
                        binding.textView38.text = userJson.getString("disc")

                        binding.textView22.text = userJson.getString("category")

                        var one:String = userJson.getString("pt_one")
                        var two:String = userJson.getString("pt_two")
                        var three:String = userJson.getString("pt_three")
                        var four:String = userJson.getString("pt_four")
                        var five:String = userJson.getString("pt_five")
                        var six:String  = userJson.getString("pt_six")


                       if (one.length==0 || one == null)
                        {
                            binding.textView15.text ="Sorry , Key features of this product are not available right now."

                        }
                        else
                        {
                            binding.textView15.text = "• "+userJson.getString("pt_one")

                        }
                        if (two.length==0 || two== null)
                        {
                            binding.textView16.visibility = View.GONE
                        }
                        else
                        {
                            binding.textView16.text = "• "+userJson.getString("pt_two")
                            binding.textView16.visibility = View.VISIBLE
                        }
                        if (three.length==0 || three == null)
                        {
                            binding.textView17.visibility = View.GONE
                        }
                        else
                        {
                            binding.textView17.text = "• "+userJson.getString("pt_three")
                            binding.textView17.visibility = View.VISIBLE
                        }

                        if (four.length==0 || four == null)
                        {
                            binding.textView18.visibility = View.GONE

                        }
                        else
                        {
                            binding.textView18.text = "• "+userJson.getString("pt_four")
                            binding.textView18.visibility = View.VISIBLE
                        }

                        if (five.length==0 || five == null)
                        {

                            binding.textView19.visibility = View.GONE


                        }
                        else
                        {
                            binding.textView19.text = "• "+userJson.getString("pt_five")
                            binding.textView19.visibility = View.VISIBLE
                        }

                        if (six.length==0 || six == null)
                        {
                            binding.textView21.visibility = View.GONE
                        }

                        else
                        {
                            binding.textView21.text = "• "+userJson.getString("pt_six")
                            binding.textView21.visibility = View.VISIBLE
                        }


//                        binding.textView15.text = "• "+userJson.getString("pt_one")
//                        binding.textView16.text = "• "+userJson.getString("pt_two")
//                        binding.textView17.text =  "• "+userJson.getString("pt_three")
//                        binding.textView18.text =  "• "+userJson.getString("pt_four")
//                        binding.textView19.text =  "• "+userJson.getString("pt_five")
//                        binding.textView21.text =  "• "+userJson.getString("pt_six")
//
                        var img1:String = userJson.getString("imageName1")
                        var img2:String = userJson.getString("imageName2")
                        var img3:String = userJson.getString("imageName3")
                        var img4:String = userJson.getString("imageName4")
                        var img5:String = userJson.getString("imageName5")
                        var img6:String = userJson.getString("imageName6")

                        if (img1.length!=0)
                        {
                            prodDescriptionImgList.add(ProductImageData(userJson.getString("image1")))
                        }
                        if (img2.length!=0)
                        {
                            prodDescriptionImgList.add(ProductImageData(userJson.getString("image2")))
                        }
                        if (img3.length!=0)
                        {
                            prodDescriptionImgList.add(ProductImageData(userJson.getString("image3")))
                        }
                        if (img4.length!=0)
                        {
                            prodDescriptionImgList.add(ProductImageData(userJson.getString("image4")))
                        }
                        if (img5.length!=0)
                        {
                            prodDescriptionImgList.add(ProductImageData(userJson.getString("image5")))
                        }
                        if (img6.length!=0)
                        {
                            prodDescriptionImgList.add(ProductImageData(userJson.getString("image6")))
                        }


                        prod_id = userJson.getString("id")
                        prod_desc_id = userJson.getString("prod_desc_id")
                        prod_im = userJson.getString("image1")
                        prod_head = userJson.getString("heading")
                        prod_ogp = userJson.getString("mrp")
                        prod_newp = userJson.getString("sale")

                        binding.prodImgList.adapter = ProductDescriptionAdapter(prodDescriptionImgList)


                        if (img1.length!=0)
                        {
                            smallProdImgList.add(SmallProductData(userJson.getString("image1")))
                        }
                        if (img2.length!=0)
                        {
                            smallProdImgList.add(SmallProductData(userJson.getString("image2")))
                        }
                        if (img3.length!=0)
                        {
                            smallProdImgList.add(SmallProductData(userJson.getString("image3")))
                        }
                        if (img4.length!=0)
                        {
                            smallProdImgList.add(SmallProductData(userJson.getString("image4")))
                        }
                        if (img5.length!=0)
                        {
                            smallProdImgList.add(SmallProductData(userJson.getString("image5")))
                        }
                        if (img6.length!=0)
                        {
                            smallProdImgList.add(SmallProductData(userJson.getString("image6")))
                        }

                        binding.smallprod.adapter = SmallProductAdapter(smallProdImgList)

                        getSimilarProducts()



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
