package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentMyAccountBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyAccountFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
var binding : FragmentMyAccountBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_account,container,false)


    return binding.root


    }


}