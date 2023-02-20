package com.example.purpleapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.purpleapp.databinding.FragmentWebviewBinding


class WebviewFragment : Fragment() {

lateinit var binding : FragmentWebviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_webview,container,false)

        val activity:MainActivity = requireActivity() as MainActivity
        activity.binding.bottomNavigationView.visibility = View.GONE

        var args = WebviewFragmentArgs.fromBundle(requireArguments())
        binding.webView.webViewClient = WebViewClient()

        // this will load the url of the website
        binding.webView.loadUrl(args.link)

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)

        return  binding.root

    }


}