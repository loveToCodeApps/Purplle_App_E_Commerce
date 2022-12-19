package com.example.purpleapp

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentHomeBinding
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //categories here
        getCategories()

        //combo offers are here
        getComboOffers()

        //banner ads here
        getProducts()

        //product details here
        getOfferProducts()

        //
        //

        //Offer banners here
        getOfferBanners()

        //Brands here
        getBrands()

        //Deals here
        getDeals()

        //New Arrivals here
        getNewArrivals()

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


        val verticalBannerList = mutableListOf<VerticalBannerData>()
        verticalBannerList.add(VerticalBannerData(R.drawable.product_four))
        verticalBannerList.add(VerticalBannerData(R.drawable.product_five))
        verticalBannerList.add(VerticalBannerData(R.drawable.product_three))
        verticalBannerList.add(VerticalBannerData(R.drawable.product_one))
        verticalBannerList.add(VerticalBannerData(R.drawable.product_two))

        binding.vertcalBannerList.adapter = VerticalBannerAdapter(verticalBannerList)


        setHasOptionsMenu(true)
        return binding.root

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

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = NewArrivalsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image")


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

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = DealsData(
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image")


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





    }

    private fun getComboOffers() {
        val comboOffersList = mutableListOf<ComboOffersData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_COMBO_OFFERS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ComboOffersData(
                                objectArtist.getString("heading"),
                                        objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image")


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

                        for (i in 2 until array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BrandProductData(
                                objectArtist.getString("url"),
                                objectArtist.getString("heading")
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
    }

    private fun getOfferBanners() {
        val bannerDiscountList = mutableListOf<BannerDiscountData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_OFFER_BANNER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length() + 1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = BannerDiscountData(
                                objectArtist.getString("url")
                            )
                            bannerDiscountList.add(banners)
                            val adapter = BannerDiscountAdapter(bannerDiscountList)
                            binding.dicountbannerList.adapter = adapter
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

                        for (i in 2 until array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = OfferProductData(
                                objectArtist.getString("id"),
                                objectArtist.getString("heading"),
                                objectArtist.getString("sale"),
                                objectArtist.getString("disc"),
                                objectArtist.getString("mrp"),
                                objectArtist.getString("image")
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
    }

    private fun getCategories() {
        var categoryList = mutableListOf<CategoryData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_CATEGORIES,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 2 until array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = CategoryData(
                                objectArtist.getString("url")
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
    }

    //    // volley request for banners
    private fun getProducts() {
        var productLists = mutableListOf<ProductData>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BANNER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ProductData(
                                objectArtist.getString("url")
                            )
                            productLists.add(banners)
                            val adapter = ProductAdapter(productLists, requireContext())
                            binding.productList.adapter = adapter
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

    }


    //----------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }
    //---------------------------------------------------------------------------------

}
