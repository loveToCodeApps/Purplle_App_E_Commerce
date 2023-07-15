package com.example.purpleapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentProductDescriptionBinding
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class ProductDescriptionFragment : Fragment() {
    lateinit var binding: FragmentProductDescriptionBinding

    // lateinit var userId:String
    var counts = "0"
    lateinit var prod_id: String
    lateinit var prod_desc_id: String
    lateinit var prod_im: String
    lateinit var prod_head: String
    lateinit var prod_ogp: String
    lateinit var prod_newp: String
    var quantitiesCounter: Int = 1
   var primary=""
    var category=""
    var subcategory=""
    var child=""
 var color:Boolean = true
    var size:Boolean = true
    var prodPrice:String="0"

    //this category is needed to pass to similarProducts() , for eg. popular/special/ products
    var urgentCategory:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_description,
            container,
            false
        )

        var showMoreDescription = "false"

        color = true
        size = true


        //get item count of cart

        getProductDescriptionDetails()
        getColors()
        getSizes()

    getSimilarProducts()

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
            } else {
                Toast.makeText(
                    requireContext(),
                    "only Logged In users can add to cart !!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }



        binding.wishlistBtn.setOnClickListener {
            if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
                addToWishlist()
//                it.findNavController().navigate(R.id.wishlistFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "only Logged In users can add to wish list !!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        binding.buyNow.setOnClickListener {
            var args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
            it.findNavController().navigate(ProductDescriptionFragmentDirections.actionProductDescriptionFragmentToShipToAddressFragment("1",prodPrice,"longbuy",args.prodId))
        }

        // plus and minus button
        binding.button13.setOnClickListener {
            if (quantitiesCounter >= 1) {
                quantitiesCounter += 1
                binding.textView23.text = quantitiesCounter.toString()
            }
        }
        binding.button15.setOnClickListener {
            if (quantitiesCounter > 1) {
                quantitiesCounter -= 1
                binding.textView23.text = quantitiesCounter.toString()
            }
        }



        binding.textView31.setOnClickListener {
            it.findNavController().navigate(
                ProductDescriptionFragmentDirections.actionProductDescriptionFragmentToCategoryAllProductsFragment(
                    primary,category,subcategory,child
                )
            )
        }


        //textview to show more description of product , bydefault it only shows 3 lines of description
        binding.textView24.setOnClickListener {
            if (showMoreDescription.equals("false")) {
                binding.textView38.maxLines = 100
                binding.textView24.text = "Show less >"
                showMoreDescription = "true"
            } else if (showMoreDescription.equals("true")) {
                binding.textView38.maxLines = 3
                binding.textView24.text = "Show more >"
                showMoreDescription = "false"
            }

        }

        setHasOptionsMenu(true)
        return binding.root

    }

    private fun getColors() {
        var colorList = mutableListOf<colorData>()
        var args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
        var product_id = args.prodId


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_COLORS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length()!=0) {
                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val banners = colorData(
                                    objectArtist.getString("pro_colour"),
                                    objectArtist.getString("desc_id"),
                                    objectArtist.getString("newpro_id")
                                    )
                                colorList.add(banners)
                                val adapter = ColorAdapter(colorList,args.prodId,binding)
                                binding.colorList.adapter = adapter
                            }
                        }
                        else
                        {
                            //hide colorlist if list length is zero
                            binding.colorList.visibility = View.GONE
                            binding.textView145.visibility = View.GONE
                            color = false
                            if (color==false && size==false)
                            {
                                binding.cardView10.visibility = View.GONE
                            }

                        }
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()

                }

            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["id"] = product_id
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)

    }

    private fun getSizes() {

        var sizesList = mutableListOf<sizeData>()

        var args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
        var product_id = args.prodId

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SIZES,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        var myfrag = ProductDescriptionFragment()

                        if (array.length()!=0) {
                            for (i in 0..array.length() - 1) {
                                   val objectArtist = array.getJSONObject(i)
                                   val banners = sizeData(
                                       objectArtist.getString("size"),
                                       objectArtist.getString("desc_id")
                                   )
                                   sizesList.add(banners)
                                   val adapter = SizeAdapter(sizesList,binding,myfrag)
                                   binding.sizeList.adapter = adapter
                            }
                        }
                        else
                        {
                            //hide colorlist if list length is zero
                            size = false
                            binding.sizeList.visibility = View.GONE
                            binding.textView146.visibility = View.GONE
                            binding.textView147.visibility = View.GONE
                            if (color==false && size==false)
                            {
                                binding.cardView10.visibility = View.GONE
                            }
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    size = false
                    binding.sizeList.visibility = View.GONE
                    binding.textView146.visibility = View.GONE
                    binding.textView147.visibility = View.GONE
                    if (color==false && size==false)
                    {
                        binding.cardView10.visibility = View.GONE
                    }
                }

            },
            Response.ErrorListener { error -> Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["id"] = product_id
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)

    }

    private fun getSimilarProducts() {
        val brandsList = mutableListOf<BrandAllProductData>()
//        binding.22.text = brand
        var args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
        var id = args.prodId
        var cat = binding.textView22.text.toString()
        if (cat==null || cat.length==0 || cat=="")
        {
            cat=urgentCategory
        }
//        categ = binding.textView22.text.toString()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SIMILAR_PRODUCTS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if(array.length()==0)
                        {
                            binding.cardView6.visibility = View.GONE
                        }
                        else {
                            //   for (i in (array.length()-1) until  1) {
                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val banners = BrandAllProductData(
                                    objectArtist.optString("heading"),
                                    objectArtist.optString("sale"),
                                    objectArtist.optString("mrp"),
                                    objectArtist.optString("image"),
                                    objectArtist.optString("id"),
                                    objectArtist.optString("name"),
                                    objectArtist.optString("disc")

                                )
                                brandsList.add(banners)
                                val adapter = SimilarProductsAdapter(brandsList)
                                binding.recyclerView2.adapter = adapter
                            }
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
                val params = java.util.HashMap<String, String>()
                params["category"] = cat
                params["id"] = id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)

    }

    private fun addToWishlist() {
        val args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())

        val userId =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_TO_WISH_LIST,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Snackbar.make(
                            requireActivity().findViewById(android.R.id.content),
                            obj.getString("message"),
                            Snackbar.LENGTH_LONG
                        ).show();
                        findNavController().navigate(R.id.wishlistFragment)

                    } else {
                        Snackbar.make(
                            requireActivity().findViewById(android.R.id.content),
                            obj.getString("message"),
                            Snackbar.LENGTH_LONG
                        ).show(); }
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
                params["prod_id"] = prod_id
                params["prod_desc_id"] = prod_desc_id
                params["user_id"] = userId
                params["unit_price"] = prod_newp
                params["total_price"] = prod_newp
                params["confirm_mobile"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.phone
                params["number_of_items"] = "1"
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)


    }

    private fun getProductDescriptionDetails() {
        var args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
        var product_id = args.prodId
        val slideModels = mutableListOf<SlideModel>()
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


                        primary = userJson.getString("primary")
                        category = userJson.getString("category")
                        subcategory = userJson.getString("subcategory")
                        child = userJson.getString("child_category")


                            // Heading of product
                        if (userJson.getString("heading") == null || userJson.getString("heading") == "null" || (userJson.getString("heading")).length==0)
                            {
                                binding.textView9.text = "Product name not available"
                            }
                        else
                        {
                            binding.textView9.text = userJson.getString("heading")
                        }



                        // Product description
                        if (userJson.getString("disc")==null || userJson.getString("disc").length==0 || userJson.getString("disc")=="null" || userJson.getString("disc") == "")
                        {
                            binding.cardView8.visibility = View.GONE
                        }
                        else
                        {
                            binding.textView38.text = userJson.getString("disc")
                        }

                        prodPrice = userJson.getString("mrp")

                        if (userJson.getString("sale")==userJson.getString("mrp"))
                        {
                            binding.textView11.visibility = View.GONE
                            binding.textView12.visibility = View.GONE
                            binding.view9.visibility = View.GONE
                            binding.textView10.text = "₹" + userJson.getString("sale")
                        }
                        else {
                            binding.textView10.text = "₹" + userJson.getString("sale")
                            binding.textView11.text = "₹" + userJson.getString("mrp")
                            binding.textView12.text =
                                userJson.getString("discount")+ "%off"
                        }
                        if (userJson.getString("gst_type")=="inc")
                        {
                            binding.textView13.text = "Inclusive of all taxes"
                        }
                        else
                        {
                            binding.textView13.text = "Exclusive of all taxes"
                        }

                        binding.textView22.text = userJson.getString("category")

                        var one: String = userJson.getString("pt_one")
                        var two: String = userJson.getString("pt_two")
                        var three: String = userJson.getString("pt_three")
                        var four: String = userJson.getString("pt_four")
                        var five: String = userJson.getString("pt_five")
                        var six: String = userJson.getString("pt_six")

                        urgentCategory = userJson.getString("primary")

    if (one.length==0 && two.length==0 && three.length==0 && four.length==0 && five.length==0 && six.length==0)
    {
        binding.cardView3.visibility = View.GONE
    }
    else if(one.length==null && two.length==null && three.length==null && four.length==null && five.length==null && six.length==null)
    {
        binding.cardView3.visibility = View.GONE
    }
    else if(one=="null" && two=="null" && three=="null" && four=="null" && five=="null" && six=="null")
    {
        binding.cardView3.visibility = View.GONE
    }

    else {
        if (one.length == 0 || one == null) {
            binding.textView15.visibility = View.GONE
        } else {
            binding.textView15.text = "• " + userJson.getString("pt_one")

        }

        if (two.length == 0 || two == null) {
            binding.textView16.visibility = View.GONE
        } else {
            binding.textView16.text = "• " + userJson.getString("pt_two")
            binding.textView16.visibility = View.VISIBLE
        }


        if (three.length == 0 || three == null) {
            binding.textView17.visibility = View.GONE
        } else {
            binding.textView17.text = "• " + userJson.getString("pt_three")
            binding.textView17.visibility = View.VISIBLE
        }



        if (four.length == 0 || four == null) {
            binding.textView18.visibility = View.GONE

        } else {
            binding.textView18.text = "• " + userJson.getString("pt_four")
            binding.textView18.visibility = View.VISIBLE
        }



        if (five.length == 0 || five == null) {

            binding.textView19.visibility = View.GONE


        } else {
            binding.textView19.text = "• " + userJson.getString("pt_five")
            binding.textView19.visibility = View.VISIBLE
        }



        if (six.length == 0 || six == null) {
            binding.textView21.visibility = View.GONE
        } else {
            binding.textView21.text = "• " + userJson.getString("pt_six")
            binding.textView21.visibility = View.VISIBLE
        }

    }

                        var img1: String = userJson.getString("imageName1")
                        var img2: String = userJson.getString("imageName2")
                        var img3: String = userJson.getString("imageName3")
                        var img4: String = userJson.getString("imageName4")
                        var img5: String = userJson.getString("imageName5")
                        var img6: String = userJson.getString("imageName6")
                        var img7: String = userJson.getString("imageName7")

                        if (img1 != null && img1.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image1"),ScaleTypes.FIT))
                        }
                        else {
                            slideModels.add(SlideModel("https://icon-library.com/images/no-picture-available-icon/no-picture-available-icon-1.jpg"))

                        }

                        if (img2 != null && img2.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image2"),ScaleTypes.FIT))
                        }
//                        else {
//                            slideModels.add(SlideModel(R.drawable.not_available_picture))
//
//                        }
                        if (img3 != null && img3.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image3"),ScaleTypes.FIT))
                        }
//                        else {
//                            slideModels.add(SlideModel(R.drawable.not_available_picture))
//
//                        }
                        if (img4 != null && img4.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image4"),ScaleTypes.FIT))
                        }
//                        else {
//                            slideModels.add(SlideModel(R.drawable.not_available_picture))

//                        }
                        if (img5 != null && img5.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image5"),ScaleTypes.FIT))
                        }
//                        else {
//                            slideModels.add(SlideModel(R.drawable.not_available_picture))
//
//                        }
                        if (img6 != null && img6.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image6"),ScaleTypes.FIT))
                        }
//                        else {
//                            slideModels.add(SlideModel(R.drawable.not_available_picture))
//
                        if (img7 != null && img7.length!=0) {
                            slideModels.add(SlideModel(userJson.getString("image7"),ScaleTypes.FIT))
                        }

//                        }

                        binding.prodImgList.setImageList(slideModels, ScaleTypes.FIT);
                        binding.prodImgList.setItemClickListener(object : ItemClickListener {
                            override fun onItemSelected(position: Int) {
                                Toast.makeText(
                                    requireContext(),
                                    "Pinch image to zoom-in or zoom-out",
                                    Toast.LENGTH_SHORT
                                ).show();
                                val mBuilder: AlertDialog.Builder =
                                    AlertDialog.Builder(requireContext())
                                val mView: View = LayoutInflater.from(requireContext())
                                    .inflate(R.layout.dialog_custom_layout, null)
//            inflate(R.layout.dialog_custom_layout, null)
                                val photoView: PhotoView = mView.findViewById(R.id.imageView)
                                Picasso.get().load(slideModels.get(position).imageUrl)
                                    .into(photoView)
                                mBuilder.setView(mView)
                                val mDialog: AlertDialog = mBuilder.create()
                                mDialog.show()
                            }
                        })



                        prod_id = userJson.getString("id")
                        prod_desc_id = userJson.getString("prod_desc_id")
                        prod_im = userJson.getString("image1")
                        prod_head = userJson.getString("heading")
                        prod_ogp = userJson.getString("mrp")
                        prod_newp = userJson.getString("sale")

                        //  binding.prodImgList.adapter = ProductDescriptionAdapter(prodDescriptionImgList)


                        if (img1.length != 0 && img1 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image1")))
                        }
                        if (img2.length != 0 && img2 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image2")))
                        }
                        if (img3.length != 0 && img3 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image3")))
                        }
                        if (img4.length != 0 && img4 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image4")))
                        }
                        if (img5.length != 0 && img5 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image5")))
                        }
                        if (img6.length != 0 && img6 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image6")))
                        }
                        if (img7.length != 0 && img7 != null) {
                            smallProdImgList.add(SmallProductData(userJson.getString("image7")))
                        }

                        binding.smallprod.adapter = SmallProductAdapter(smallProdImgList)

                        getSimilarProducts()


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
                params["id"] = product_id

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun addToCart() {

        val args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())

        val userId =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_TO_CART,
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

                     findNavController().navigate(R.id.myCartFragment)

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
                params["prod_id"] = prod_id
                params["prod_desc_id"] = prod_desc_id
                params["user_id"] = userId
                params["unit_price"] = prod_newp
                params["total_price"] = prod_newp
                params["confirm_mobile"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.phone
                params["number_of_items"] = "1"


                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)


        //  findNavController().navigate(R.id.myCartFragment)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_description_menu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.myCartFragment)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myCartFragment ->
            {
                if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
                    return NavigationUI.onNavDestinationSelected(
                        item!!,
                        requireView().findNavController()
                    ) || super.onOptionsItemSelected(item)
                }
                else
                {
                    Toast.makeText(requireContext(),"you will first need to login !!",Toast.LENGTH_SHORT).show()
                    return true
                }
            }
            else->
            {
                return NavigationUI.onNavDestinationSelected(
                    item!!,
                    requireView().findNavController()
                ) || super.onOptionsItemSelected(item)
            }
        }

//        if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
//            return NavigationUI.onNavDestinationSelected(
//                item!!,
//                requireView().findNavController()
//            ) || super.onOptionsItemSelected(item)
//        }
//        else
//        {
//            Toast.makeText(requireContext(),"you will first need to login !!",Toast.LENGTH_SHORT).show()
//            return true
//        }

        }

    // Creating our Share Intent
    private fun getShareIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "pjfpejgpoejvpoj")
        return shareIntent
    }
}


