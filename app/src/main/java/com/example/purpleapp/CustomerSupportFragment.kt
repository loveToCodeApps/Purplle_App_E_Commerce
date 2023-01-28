package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentCustomerSupportBinding

class CustomerSupportFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val binding : FragmentCustomerSupportBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_customer_support,container,false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

    return binding.root


    }

}