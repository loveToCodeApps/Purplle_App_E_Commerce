package com.example.purpleapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.purpleapp.databinding.FragmentProductDescriptionBinding

class ProductDescriptionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentProductDescriptionBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_description,container,false)


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



        setHasOptionsMenu(true)
        return binding.root



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_description_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}