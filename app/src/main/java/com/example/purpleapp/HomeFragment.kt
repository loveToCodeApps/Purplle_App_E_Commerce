package com.example.purpleapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentHomeBinding
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {
lateinit var binding : FragmentHomeBinding
private var productList: MutableList<ProductData>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        val categoryList = mutableListOf<CategoryData>()
        categoryList.add(CategoryData("offers",R.drawable.percentage))
        categoryList.add(CategoryData("skincare",R.drawable.face_cream))
        categoryList.add(CategoryData("haircare",R.drawable.shampoo))
        categoryList.add(CategoryData("makeup",R.drawable.makeup))
        categoryList.add(CategoryData("new launch",R.drawable.new_launch))
       binding.categoryList.adapter = CategoryAdapter(categoryList)


        productList = mutableListOf<ProductData>()
//        productList.add(ProductData("choose your free gift on 499/-","UPTO 35% OFF /-",R.drawable.product_one))
//        productList.add(ProductData("you are very lucky","It's a new launch /-",R.drawable.product_two))
//        productList.add(ProductData("bring diamond glow to your skin","offer is ending soon",R.drawable.product_three))
//        productList.add(ProductData("choose your free gift on 1499/-","most sold out product",R.drawable.product_four))
//        productList.add(ProductData("made from special herbs","UPTO 55% OFF /-",R.drawable.product_five))
        getProducts()



        val offerProductList = mutableListOf<OfferProductData>()
        offerProductList.add(OfferProductData("AloeVera moisturiser","₹123  13% off","₹123",R.drawable.offer_prod_one))
        offerProductList.add(OfferProductData("Beauty kit","₹499  13% off","₹499",R.drawable.offer_prod_two))
        offerProductList.add(OfferProductData("Supreme Straightner","₹123  20% off","₹123",R.drawable.offer_prod_three))
        offerProductList.add(OfferProductData("Maibeline Glow","₹49.59  12% off","₹49.59",R.drawable.offer_prod_four))
        offerProductList.add(OfferProductData("Fairness Magic","₹38.90  70% off","₹38.90",R.drawable.offer_prod_five))
        binding.offerProductList.adapter = OfferProductAdapter(offerProductList)


        val liveProductList = mutableListOf<LiveProductData>()
        liveProductList.add(LiveProductData("₹38.90",R.drawable.offer_prod_five))
        liveProductList.add(LiveProductData("₹49.59",R.drawable.offer_prod_four))
        liveProductList.add(LiveProductData("₹123",R.drawable.offer_prod_three))
        liveProductList.add(LiveProductData("₹499",R.drawable.offer_prod_two))
        liveProductList.add(LiveProductData("₹123",R.drawable.offer_prod_one))

        binding.liveProductList.adapter = LiveProductAdapter(liveProductList)


        val bannerDiscountList = mutableListOf<BannerDiscountData>()
        bannerDiscountList.add(BannerDiscountData(R.drawable.product_five))
        bannerDiscountList.add(BannerDiscountData(R.drawable.product_four))
        bannerDiscountList.add(BannerDiscountData(R.drawable.product_three))
        bannerDiscountList.add(BannerDiscountData(R.drawable.product_two))
        bannerDiscountList.add(BannerDiscountData(R.drawable.product_one))

        binding.dicountbannerList.adapter = BannerDiscountAdapter(bannerDiscountList)


        val brandProductList = mutableListOf<BrandProductData>()
        brandProductList.add(BrandProductData(R.drawable.offer_prod_one,"L A K M E","Extra 10% off\\non Every Order"))
        brandProductList.add(BrandProductData(R.drawable.offer_prod_two,"G A R N I E R","Extra 8% off\\non Every Order"))
        brandProductList.add(BrandProductData(R.drawable.offer_prod_three,"N I V E A","Extra 34% off\\non Every Order"))
        brandProductList.add(BrandProductData(R.drawable.offer_prod_four,"A n g e l i n a","Extra 21% off\\non Every Order"))
        brandProductList.add(BrandProductData(R.drawable.offer_prod_five,"M A Y B E L L I N E","Extra 18% off\\non Every Order"))

        binding.brandProductCategoryList.adapter = BrandProductAdapter(brandProductList)


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

    // volley request for banners
    private fun getProducts() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BANNER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            val banners = ProductData(
                                objectArtist.getInt("id"),
                                objectArtist.getString("url")
                            )
                            productList!!.add(banners)
                            val adapter = ProductAdapter(productList!!)
                           binding.productList.adapter = adapter
                        }
                    } else {
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError -> Toast.makeText(requireContext(), volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

}


    //----------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
   //---------------------------------------------------------------------------------

}







