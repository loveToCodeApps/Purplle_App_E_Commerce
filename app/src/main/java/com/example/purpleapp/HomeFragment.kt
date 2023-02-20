package com.example.purpleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.purpleapp.api.Internet
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var activityAlreadyCreated:Boolean = false
    var searchList =  arrayListOf<BrandNameData>()
    lateinit var adapter : BrandNameAdapter
    lateinit var adapters : ArrayAdapter<String>
    var count = "0"





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val i1 = Internet()


        Log.i("@@@@","onCreateView() called")
//    if (b)
//    {
//        binding.categoryNamesList.visibility = View.VISIBLE
//    }
//    else
//    {
//        binding.categoryNamesList.visibility = View.GONE
//    }
//
//}
//
//        binding.searchView.setOnClickListener {
//            it.findNavController().navigate(R.id.searchAnything)
//        }

        binding.searchView.setOnEditorActionListener { v, actionId, event ->
           if (binding.searchView.length()!=0) {
               if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   Toast.makeText(requireContext(), "searched your query", Toast.LENGTH_SHORT)
                       .show() // you can do anything
                   findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchedProductsFragment(v.text.toString()))
                   return@setOnEditorActionListener true
               }
               false
           }
            else
           {
               Toast.makeText(requireContext(), "please enter something first!!", Toast.LENGTH_SHORT).show()
           }
            false
           }


        if (i1.checkConnection(requireContext())) {
            binding.animationView.visibility = View.GONE
            binding.textView103.visibility=View.VISIBLE
            binding.categoryList.visibility = View.VISIBLE
            binding.textView95.visibility= View.VISIBLE
            binding.textView2.visibility= View.VISIBLE
            binding.textView3.visibility= View.VISIBLE
            binding.textView87.visibility= View.VISIBLE
            binding.textView98.visibility= View.VISIBLE
            binding.textView99.visibility= View.VISIBLE
            binding.comboOffersList.visibility= View.VISIBLE
            binding.productList.visibility= View.VISIBLE
            binding.offerProductList.visibility= View.VISIBLE
            binding.newArrivalsList.visibility= View.VISIBLE
            binding.liveProductList.visibility= View.VISIBLE
            binding.dicountbannerList.visibility= View.VISIBLE
            binding.brandProductCategoryList.visibility= View.VISIBLE
            binding.vertcalBannerList.visibility= View.VISIBLE
            binding.textView134.visibility= View.VISIBLE
            binding.textView135.visibility= View.VISIBLE
            binding.textView136.visibility= View.VISIBLE
            binding.textView138.visibility= View.VISIBLE
            binding.searchView.visibility = View.VISIBLE
            binding.randomList.visibility= View.VISIBLE

            //get item count of cart
            if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
                getCartItemsCount()
            }
            else
            {
                count = "0"
            }

            //categories here
             getCategories()

             //combo offers are here
             getComboOffers()

             //banner ads here
             getProducts()

             //product details here
             getOfferProducts()

             //Offer banners here
             getOfferBanners()

             //Brands here
             getBrands()

             //Deals here
             getDeals()

             //New Arrivals here
             getNewArrivals()

            // last random list banners are here
            getRandomBanners()

            //get category names for searchview
            getCategoryNames()

             }
        else
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Poor internet connection!!", Snackbar.LENGTH_LONG).show();
            binding.animationView.visibility = View.VISIBLE
            binding.textView103.visibility=View.GONE
          binding.categoryList.visibility = View.GONE
          binding.textView95.visibility= View.GONE
          binding.textView2.visibility= View.GONE
          binding.textView3.visibility= View.GONE
            binding.textView87.visibility= View.GONE
            binding.textView98.visibility= View.GONE
            binding.textView99.visibility= View.GONE
            binding.comboOffersList.visibility= View.GONE
            binding.productList.visibility= View.GONE
            binding.offerProductList.visibility= View.GONE
            binding.newArrivalsList.visibility= View.GONE
            binding.liveProductList.visibility= View.GONE
            binding.dicountbannerList.visibility= View.GONE
            binding.brandProductCategoryList.visibility= View.GONE
            binding.vertcalBannerList.visibility= View.GONE
            binding.textView134.visibility= View.GONE
            binding.textView135.visibility= View.GONE
            binding.textView136.visibility= View.GONE
            binding.searchView.visibility = View.GONE
            binding.textView138.visibility= View.GONE
            binding.randomList.visibility= View.GONE



        }

        binding.textView138.setOnClickListener {
            it.findNavController().navigate(R.id.brandFragment)
        }


        // searchview item onclick listners
        binding.searchView.setOnItemClickListener { adapterAdapterView, view, i, l ->
         findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchedProductsFragment(adapters.getItem(i).toString()))

        }


//


//        val liveProductList = mutableListOf<LiveProductData>()
//        liveProductList.add(LiveProductData("₹38.90", R.drawable.offer_prod_five))
//        liveProductList.add(LiveProductData("₹49.59", R.drawable.offer_prod_four))
//        liveProductList.add(LiveProductData("₹123", R.drawable.offer_prod_three))
//        liveProductList.add(LiveProductData("₹499", R.drawable.offer_prod_two))
//        liveProductList.add(LiveProductData("₹123", R.drawable.offer_prod_one))

   //     binding.liveProductList.adapter = LiveProductAdapter(liveProductList)


//        val bannerDiscountList = mutableListOf<BannerDiscountData>()
//        bannerDiscountList.add(BannerDiscountData(R.drawable.product_five))
//        bannerDiscountList.add(BannerDiscountData(R.drawable.product_four))
//        bannerDiscountList.add(BannerDiscountData(R.drawable.product_three))
//        bannerDiscountList.add(BannerDiscountData(R.drawable.product_two))
//        bannerDiscountList.add(BannerDiscountData(R.drawable.product_one))
//
//        binding.dicountbannerList.adapter = BannerDiscountAdapter(bannerDiscountList)


//        val brandProductList = mutableListOf<BrandProductData>()
//        brandProductList.add(BrandProductData(R.drawable.offer_prod_one,"L A K M E","Extra 10% off\\non Every Order"))
//        brandProductList.add(BrandProductData(R.drawable.offer_prod_two,"G A R N I E R","Extra 8% off\\non Every Order"))
//        brandProductList.add(BrandProductData(R.drawable.offer_prod_three,"N I V E A","Extra 34% off\\non Every Order"))
//        brandProductList.add(BrandProductData(R.drawable.offer_prod_four,"A n g e l i n a","Extra 21% off\\non Every Order"))
//        brandProductList.add(BrandProductData(R.drawable.offer_prod_five,"M A Y B E L L I N E","Extra 18% off\\non Every Order"))
//
//        binding.brandProductCategoryList.adapter = BrandProductAdapter(brandProductList)

//--------------------------   search categories of all types here
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                activitiesFilter(newText)
//                return true
//            }
//        })



//---------------------------
        // do uncomment -   vertical banners
//        val verticalBannerList = mutableListOf<VerticalBannerData>()
//        verticalBannerList.add(VerticalBannerData(R.drawable.product_four))
//        verticalBannerList.add(VerticalBannerData(R.drawable.product_five))
//        verticalBannerList.add(VerticalBannerData(R.drawable.product_three))
//        verticalBannerList.add(VerticalBannerData(R.drawable.product_one))
//        verticalBannerList.add(VerticalBannerData(R.drawable.product_two))
//
//        binding.vertcalBannerList.adapter = VerticalBannerAdapter(verticalBannerList)


        // to open a page where we can see all of the combo offers product
       // All combo offers
        binding.textView136.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewAllFragment("comboOffers"))
        }

        // All featured products
        binding.textView3.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewAllFragment("featured"))
        }

        // All combo offers
        binding.textView134.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewAllFragment("newArrivals"))
        }

        // All hot deals
        binding.textView135.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewAllFragment("hotDeals"))
        }

        setHasOptionsMenu(true)
        return binding.root

    }

    private fun getCartItemsCount() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_CART_ITEMS_COUNT,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                         count = obj.getString("count")


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
                        .trim()
                return params

            }
        }

//        VolleySingleton.getInstance(requireActivity().applicationContext)
//            .addToRequestQueue(stringRequest)


        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

      requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }


    }

    private fun activitiesFilter(newText: String?) {
        Log.i("@@@@@@@@@@@@@@@@@@@@","$newText")
        var newFilteredList = arrayListOf<BrandNameData>()

        for (i in searchList)
        {
            if (i.brand.contains(newText!!.uppercase()) || i.brand.contains(newText!!.lowercase())){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
    }



    // for searchview
    private fun getCategoryNames() {
        val brandProductList = mutableListOf<BrandNameData>()
        val newlist = ArrayList<String>()

        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_CATEGORY_NAMES_ALL,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                         newlist.add(objectArtist.getString("category"))
                            adapters = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,newlist)
                            binding.searchView.setAdapter(adapters)


                           // binding.searchView.adapter = adapter


                        //    binding.categoryNamesList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

       val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }


    //when you search something in searchview


    private fun getRandomBanners() {
        val slideModels = mutableListOf<SlideModel>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BANNER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ProductData(
                                objectArtist.getString("image"),
                                objectArtist.getString("url")
                                )
                            // productLists.add(banners)

                            //   val adapter = ProductAdapter(productLists, requireContext())
                            //   binding.productList.adapter = adapter

                            slideModels.add(SlideModel(banners.url))
                            binding.randomList.setImageList(slideModels, ScaleTypes.FIT);

                            binding.randomList.setItemClickListener(object : ItemClickListener {
                                override fun onItemSelected(position: Int) {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        "it got clicked",
//                                        Toast.LENGTH_SHORT
//                                    ).show();
                                    val onc = array.getJSONObject(position)
                                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWebviewFragment( onc.getString("url")))


                                }
                            })

                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }

    override fun onPause() {
        super.onPause()
        Log.i("@@@@","onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("@@@@","onStop() called")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("@@@@","onDestroyView() called")
        //activityAlreadyCreated=false

    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("@@@@","onDestroy() called")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("@@@@","onCreate() called")
    }

    private fun getNewArrivals() {
        val newArrivalsList = mutableListOf<NewArrivalsData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_NEW_ARRIVALS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = NewArrivalsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id"),
                                objectArtist.getString("name")



                                )
                            newArrivalsList.add(banners)
                            val adapter = NewArrivalsAdapter(newArrivalsList)
                            binding.newArrivalsList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }


    }

    private fun getDeals() {
        val dealsList = mutableListOf<DealsData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_HOT_DEALS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = DealsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id"),
                                objectArtist.getString("name")

                            )
                            dealsList.add(banners)
                            val adapter = DealsAdapter(dealsList)
                            binding.liveProductList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }




    }

    private fun getComboOffers() {
        Log.i("@@@@","volley fired")
        val comboOffersList = mutableListOf<ComboOffersData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_COMBO_OFFERS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")


                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ComboOffersData(
                                objectArtist.getString("heading"),
                                        objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("id"),
                                objectArtist.getString("name")
                                )
                            comboOffersList.add(banners)
                            val adapter = ComboOffersAdspter(comboOffersList)
                            binding.comboOffersList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        stringRequest.retryPolicy =
            DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }

    private fun getBrands() {
        val brandProductList = mutableListOf<BrandProductData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BRANDS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BrandProductData(
                                objectArtist.getString("url"),
                                objectArtist.getString("heading"),
                                objectArtist.getString("name")
                            )

                            brandProductList.add(banners)
                            val adapter = BrandProductAdapter(brandProductList)
                            binding.brandProductCategoryList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }

    private fun getOfferBanners() {
      //  val bannerDiscountList = mutableListOf<BannerDiscountData>()
        val slideModels = mutableListOf<SlideModel>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_OFFER_BANNER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 ..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BannerDiscountData(
                                objectArtist.getString("image"),
                                objectArtist.getString("url")
                            )
//                            bannerDiscountList.add(banners)
                           // val adapter = BannerDiscountAdapter(bannerDiscountList)
//                            binding.dicountbannerList.adapter = adapter

                            slideModels.add(SlideModel(banners.banner))
                            binding.dicountbannerList.setImageList(slideModels, ScaleTypes.FIT);
                            binding.dicountbannerList.setItemClickListener(object : ItemClickListener {
                                override fun onItemSelected(position: Int) {
//
                                    val onc = array.getJSONObject(position)
                                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWebviewFragment( onc.getString("url")))


                                }
                            })
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }


    private fun getOfferProducts() {
        val offerProductList = mutableListOf<OfferProductData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_FEATURED_PRODUCTS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0.. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = OfferProductData(
                                objectArtist.getString("id"),
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image"),
                                objectArtist.getString("name")
                            )

                            offerProductList.add(banners)
                            val adapter = OfferProductAdapter(offerProductList)
                            binding.offerProductList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }

    private fun getCategories() {
        activityAlreadyCreated=true
        Log.i("@@@@","volley fired")
        var categoryList = mutableListOf<CategoryData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_CATEGORIES,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = CategoryData(
                                objectArtist.getString("url"),
                                objectArtist.getString("heading"),
                                objectArtist.getString("img_name")
                                )
                            categoryList.add(banners)
                            val adapter = CategoryAdapter(categoryList)
                            binding.categoryList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }

    //    // volley request for banners
    private fun getProducts() {
      //  var productLists = mutableListOf<ProductData>()
        val slideModels = mutableListOf<SlideModel>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BANNER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 .. array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ProductData(
                                objectArtist.getString("image"),
                                objectArtist.getString("url")

                                )
                           // productLists.add(banners)

                         //   val adapter = ProductAdapter(productLists, requireContext())
                         //   binding.productList.adapter = adapter

                            slideModels.add(SlideModel(banners.url))
                            binding.productList.setImageList(slideModels, ScaleTypes.FIT);
                            binding.productList.setItemClickListener(object : ItemClickListener {
                                override fun onItemSelected(position: Int) {
                                    val onc = array.getJSONObject(position)
                                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWebviewFragment( onc.getString("url")))

                                }
                            })
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

        requestQueue.addRequestFinishedListener<Any> { requestQueue.cache.clear() }

    }


    //----------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        val menuItem:MenuItem = menu.findItem(R.id.myCartFragment)
        val loginStatus:MenuItem = menu.findItem(R.id.loginBtn)
        val actionView:View = menuItem.actionView
        if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn)
        {
            loginStatus.title = "Logout"
        }
        else
        {
            loginStatus.title = "Login or Register"
        }

        var cartBadgeTextview:TextView = actionView.findViewById(R.id.cart_badge_text_view)

        if (count==null || count=="null" || count=="") {
            cartBadgeTextview.setText("0")
        }
        else
        {
            cartBadgeTextview.setText(count)
        }
        actionView.setOnClickListener {
          if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn) {
              onOptionsItemSelected(menuItem)
          }
            else
          {
              val intent = Intent(requireContext(), LoginActivity::class.java)
              startActivity(intent)
              requireActivity().finish()
          }
          }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}
