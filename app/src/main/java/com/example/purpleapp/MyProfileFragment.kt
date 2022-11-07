package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import com.example.purpleapp.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
var binding : FragmentMyProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_profile,container,false)

   binding.imageView23.setOnClickListener {
       it.findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
   }

        binding.imageView25.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_myCartFragment)
        }
        binding.imageView26.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_wishlistFragment)
        }

    return  binding.root

    }


}