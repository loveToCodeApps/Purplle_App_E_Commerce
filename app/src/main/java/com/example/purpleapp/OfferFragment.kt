package com.example.purpleapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.purpleapp.databinding.FragmentOfferBinding

class OfferFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val binding : FragmentOfferBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_offer,container,false)


        val offerForYouList = mutableListOf<OfferForYouData>()
        offerForYouList.add(OfferForYouData("choose your free gift on 499/-","UPTO 35% OFF /-",R.drawable.product_one))
        offerForYouList.add(OfferForYouData("you are very lucky","It's a new launch /-",R.drawable.product_two))
        offerForYouList.add(OfferForYouData("bring diamond glow to your skin","offer is ending soon",R.drawable.product_three))
        offerForYouList.add(OfferForYouData("choose your free gift on 1499/-","most sold out product",R.drawable.product_four))
        offerForYouList.add(OfferForYouData("made from special herbs","UPTO 55% OFF /-",R.drawable.product_five))
        binding.offersForYouList.adapter =OfferForYouAdapter(offerForYouList)






        setHasOptionsMenu(true)

    return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.offer_menu,menu)    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}




