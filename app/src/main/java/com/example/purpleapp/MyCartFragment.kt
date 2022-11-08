package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentMyCartBinding

class MyCartFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
val binding:FragmentMyCartBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_cart,container,false)

        val myCartDataList = mutableListOf<MyCartData>()
        myCartDataList.add(MyCartData(R.drawable.offer_prod_two,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myCartDataList.add(MyCartData(R.drawable.offer_prod_one,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myCartDataList.add(MyCartData(R.drawable.offer_prod_four,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myCartDataList.add(MyCartData(R.drawable.offer_prod_three,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myCartDataList.add(MyCartData(R.drawable.offer_prod_five,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myCartDataList.add(MyCartData(R.drawable.offer_prod_two,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))

        binding.myCartProdList.adapter = MyCartAdapter(myCartDataList)

    return binding.root

    }


}