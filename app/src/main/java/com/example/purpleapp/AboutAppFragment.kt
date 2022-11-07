package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.purpleapp.databinding.FragmentAboutAppBinding


class AboutAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
val binding : FragmentAboutAppBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_about_app,container,false)

        val logo = AnimationUtils.loadAnimation(requireContext(),R.anim.left_to_center)
        binding.imageView19.animation= logo

        val heading = AnimationUtils.loadAnimation(requireContext(),R.anim.fade)
        binding.textView28.animation=heading

        return binding.root

    }


}