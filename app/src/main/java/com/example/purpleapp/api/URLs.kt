package com.example.purpleapp.api

object URLs {
        private val ROOT_URL = "http://192.168.0.106/affetta_api/registrationapi.php?apicall="
        val URL_REGISTER = ROOT_URL + "signup"
        val URL_LOGIN = ROOT_URL + "login"
        val URL_GET_BANNER = ROOT_URL + "bannerAds"
        val URL_GET_CATEGORIES = ROOT_URL + "categories"
        val URL_GET_SUB_CATEGORIES = ROOT_URL + "subCategories"
        val URL_GET_FEATURED_PRODUCTS = ROOT_URL + "featured"
        val URL_GET_OFFER_BANNER = ROOT_URL + "offerBanners"
        val URL_GET_BRANDS = ROOT_URL + "brands"
        val URL_GET_COMBO_OFFERS = ROOT_URL + "comboOffers"
        val URL_GET_NEW_ARRIVALS = ROOT_URL + "newArrivals"
        val URL_GET_HOT_DEALS = ROOT_URL + "deals"
        val URL_GET_PARTICULAR_CATEGORY_PRODUCTS = ROOT_URL + "getParticularCategoryProducts"

}

