package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentMyOrdersBinding

class MyOrdersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
val binding : FragmentMyOrdersBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_orders,container,false)

        val myOrdersDataList = mutableListOf<MyOrdersData>()
        myOrdersDataList.add(MyOrdersData(R.drawable.offer_prod_two,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myOrdersDataList.add(MyOrdersData(R.drawable.offer_prod_one,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myOrdersDataList.add(MyOrdersData(R.drawable.offer_prod_four,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myOrdersDataList.add(MyOrdersData(R.drawable.offer_prod_three,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myOrdersDataList.add(MyOrdersData(R.drawable.offer_prod_five,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))
        myOrdersDataList.add(MyOrdersData(R.drawable.offer_prod_two,"TNW - The natural wash pure gold neem oil for skin and hair (100 ml) ","₹324","476","12%","★4.2","213","12"))

        binding.myOrdersList.adapter = MyOrdersAdapter(myOrdersDataList)
        return binding.root

    }


}