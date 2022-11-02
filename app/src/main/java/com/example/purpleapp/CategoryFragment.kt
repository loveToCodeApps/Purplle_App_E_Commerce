package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
var binding : FragmentCategoryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_category,container,false)


        val serviceCategoryList = mutableListOf<ServiceCategoryData>()
        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_one,"MakeUp V"))
        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_three,"SkinCare V"))
        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_two,"HairCare V"))
        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_five,"Men V"))
        serviceCategoryList.add(ServiceCategoryData(R.drawable.girl_four,"Fragrance V"))


        binding.serviceCategoryList.adapter = ServiceCategoryAdapter(serviceCategoryList)

        return binding.root

    }


}