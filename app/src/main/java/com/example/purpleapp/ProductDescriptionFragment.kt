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
import com.example.purpleapp.api.VolleySingleton
import com.example.purpleapp.databinding.FragmentProductDescriptionBinding
import org.json.JSONException
import org.json.JSONObject

class ProductDescriptionFragment : Fragment() {

// lateinit var userId:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentProductDescriptionBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_description,container,false)

//        if (SharedPrefManager.getInstance(requireActivity().applicationContext).isLoggedIn)
//        {
//            userId = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id.toString()
//        }

       val prodDescriptionImgList = mutableListOf<ProductImageData>()
       prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_one))
        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_two))
        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_three))
        prodDescriptionImgList.add(ProductImageData(R.drawable.demo_prod_four))

        binding.prodImgList.adapter = ProductDescriptionAdapter(prodDescriptionImgList)



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

        setHasOptionsMenu(true)
        return binding.root


0
    }

    private fun addToCart() {

        val args = ProductDescriptionFragmentArgs.fromBundle(requireArguments())
        val id = args.oldProductData.id
        val mrp = args.oldProductData.mrp
        val heading = args.oldProductData.heading
        val disc = args.oldProductData.disc
        val sale = args.oldProductData.sale
        val userId = args.oldProductData.id

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
                params["prod_id"] = id.toString()
                params["user_id"] = userId.toString()
                params["quantity"] = "1"
                params["mrp"] = mrp.toString()

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