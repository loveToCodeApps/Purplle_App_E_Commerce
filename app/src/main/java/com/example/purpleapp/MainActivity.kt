package com.example.purpleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.purpleapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // action bar title changed
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        drawerLayout = binding.myDrawer


        val navController = this.findNavController(R.id.purplleNavHost)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.myNavView, navController)
        binding.bottomNavigationView.setupWithNavController(navController)





    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.purplleNavHost)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }




}






