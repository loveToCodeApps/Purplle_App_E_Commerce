package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentBrandBinding

class BrandFragment : Fragment() {
  

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val binding : FragmentBrandBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_brand,container,false)

        val brandNameList = mutableListOf<BrandNameData>()
        brandNameList.add(BrandNameData("All Brands"))
        brandNameList.add(BrandNameData("   A      Albion  "))
        brandNameList.add(BrandNameData("          Anastasia"))
        brandNameList.add(BrandNameData("          Aqua"))
        brandNameList.add(BrandNameData("   B      Balmshell"))
        brandNameList.add(BrandNameData("          Bayankala"))
        brandNameList.add(BrandNameData("          Beautycounter"))
        brandNameList.add(BrandNameData("   C      Carmex"))
        brandNameList.add(BrandNameData("          Channel"))
        brandNameList.add(BrandNameData("          Cliniq"))
        brandNameList.add(BrandNameData("   D      Dermacol"))
        brandNameList.add(BrandNameData("   E      Eisenberg"))
        brandNameList.add(BrandNameData("          Elizabeth"))
        brandNameList.add(BrandNameData("   F      Fenty Beauty"))
        brandNameList.add(BrandNameData("          Forest Essentials"))
        brandNameList.add(BrandNameData("          First Love"))
        brandNameList.add(BrandNameData("   G      Glow & Lovely"))
        brandNameList.add(BrandNameData("   H      Harda Labo"))
        brandNameList.add(BrandNameData("          Halo Beauty"))
        brandNameList.add(BrandNameData("          Helo Taco"))
        brandNameList.add(BrandNameData("   L      L'oreal"))
        brandNameList.add(BrandNameData("          Lakme"))
        brandNameList.add(BrandNameData("          Crystal Beauty"))
//        brandNameList.add(BrandNameData(""))
//        brandNameList.add(BrandNameData(""))
//        brandNameList.add(BrandNameData(""))
//        brandNameList.add(BrandNameData(""))
//        brandNameList.add(BrandNameData(""))
//        brandNameList.add(BrandNameData(""))

       binding.brandNameList.adapter = BrandNameAdapter(brandNameList)
    
    
    return binding.root
    }

   
}