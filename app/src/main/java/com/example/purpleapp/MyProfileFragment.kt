package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.databinding.FragmentMyProfileBinding
import com.google.android.material.snackbar.Snackbar

class MyProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
val binding : FragmentMyProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_profile,container,false)

   binding.imageView23.setOnClickListener {
       it.findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
   }

        binding.imageView25.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_myCartFragment)
        }
        binding.imageView26.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_wishlistFragment)
        }

        binding.textView75.setOnClickListener {
            it.findNavController().navigate(R.id.action_myProfileFragment_to_customerSupportFragment  )
        }



        binding.textView69.text=SharedPrefManager.getInstance(requireActivity().applicationContext).user.firstName+" "+
                SharedPrefManager.getInstance(requireActivity().applicationContext).user.lastName

        binding.textView77.text=SharedPrefManager.getInstance(requireActivity().applicationContext).user.email


        // Logout button code
        binding.button7.setOnClickListener {
            Toast.makeText(requireContext(),"Logout Successfully !!",Toast.LENGTH_SHORT).show()
//            Snackbar.make(
//                requireActivity().findViewById(android.R.id.content),
//                "Logout Successfully !!",
//                Snackbar.LENGTH_SHORT
//            ).show()
            SharedPrefManager.getInstance(requireActivity().applicationContext).logout()

            requireActivity().finish()
        }


    return  binding.root

    }


}



