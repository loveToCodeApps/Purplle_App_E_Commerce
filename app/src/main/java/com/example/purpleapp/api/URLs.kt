package com.example.purpleapp.api

object URLs {
        private val ROOT_URL = "http://192.168.0.105/affetta_api/registrationapi.php?apicall="
        val URL_REGISTER = ROOT_URL + "signup"
        val URL_LOGIN = ROOT_URL + "login"
        val URL_GET_BANNER = ROOT_URL + "bannerAds"
        val URL_GET_CATEGORIES = ROOT_URL + "categories"
        val URL_GET_FEATURED_PRODUCTS = ROOT_URL + "featured"
}
