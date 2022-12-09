package com.example.purpleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.databinding.ActivityMainBinding

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


        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val user = SharedPrefManager.getInstance(this).user
            binding.textView87.text = user.firstName.toString()
        }


//        }
//        else {
//            val intent = Intent(this@MainActivity, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }


        //bottom navigation view listners
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myProfile -> if (!SharedPrefManager.getInstance(this).isLoggedIn) {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                } else {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myProfileFragment)
                }
                R.id.homeFragment -> findNavController(R.id.purplleNavHost)
                    .navigate(R.id.homeFragment)
                R.id.categoryFragment -> findNavController(R.id.purplleNavHost)
                    .navigate(R.id.categoryFragment)
                R.id.brandFragment -> findNavController(R.id.purplleNavHost)
                    .navigate(R.id.brandFragment)
                R.id.offerFragment -> findNavController(R.id.purplleNavHost)
                    .navigate(R.id.offerFragment)
            }
            true
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.purplleNavHost)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }


}






