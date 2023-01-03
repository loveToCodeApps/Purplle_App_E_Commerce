package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentShipToAddressBinding

class ShipToAddressFragment : Fragment() {

lateinit var  binding: FragmentShipToAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ship_to_address,container,false)

        val activity:MainActivity = requireActivity() as MainActivity


        activity.binding.bottomNavigationView.visibility = View.GONE


        val prodList = mutableListOf<ShipToAddressProductsData>()

        prodList.add(ShipToAddressProductsData("Natural skin glow","https://cdn.shopify.com/s/files/1/1375/4957/products/FCVitaminC_1024x1024.jpg?v=1667893691"))
        prodList.add(ShipToAddressProductsData("Natural skin glow","https://cdn.shopify.com/s/files/1/1375/4957/products/FCVitaminC_1024x1024.jpg?v=1667893691"))
        prodList.add(ShipToAddressProductsData("Natural skin glow","https://cdn.shopify.com/s/files/1/1375/4957/products/FCVitaminC_1024x1024.jpg?v=1667893691"))


        val adapter = ShipToAddressProductsAdapter(prodList)
        binding.recyclerView.adapter = adapter



    return  binding.root

    }


}