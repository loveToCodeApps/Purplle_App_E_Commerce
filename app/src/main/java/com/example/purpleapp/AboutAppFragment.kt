package com.example.purpleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purpleapp.api.URLs
import com.example.purpleapp.databinding.FragmentAboutAppBinding
import org.json.JSONException
import org.json.JSONObject


class AboutAppFragment : Fragment() {

    lateinit var binding : FragmentAboutAppBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
 binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_about_app,container,false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

        val logo = AnimationUtils.loadAnimation(requireContext(),R.anim.left_to_center_logo)
        binding.imageView19.animation= logo

        val heading = AnimationUtils.loadAnimation(requireContext(),R.anim.fade)
        binding.textView28.animation=heading


        aboutAppDescription()

        return binding.root

    }

    private fun aboutAppDescription() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_ABOUT_APP,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)

                            binding.textView30.text = objectArtist.getString("description")

                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }


}