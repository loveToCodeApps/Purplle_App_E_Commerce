package com.example.purpleapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
       //     binding.textView87.text = user.firstName.toString()
            val headerView = binding.myNavView.getHeaderView(0)
            val first_name = headerView.findViewById<TextView>(R.id.textView10)
            val last_name = headerView.findViewById<TextView>(R.id.username)
            val first = SharedPrefManager.getInstance(this).user.firstName + " "
            val last = SharedPrefManager.getInstance(this).user.lastName
            first_name.text = first
            last_name.text = last
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
                  .navigate(R.id.offersFragment)

            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.productDescriptionFragment) {

                binding.bottomNavigationView.visibility = View.GONE
            } else {

                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }


        //navigation drawer menu
        binding.myNavView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
            R.id.homeFragment-> {
                findNavController(R.id.purplleNavHost)
                    .navigate(R.id.homeFragment)
                Log.i("@@@@","${it.itemId}")
                binding.myDrawer.closeDrawer(GravityCompat.START, true)
            }

                R.id.categoryFragment->{ findNavController(R.id.purplleNavHost)
                    .navigate(R.id.categoryFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
            }
                R.id.brandFragment->{ findNavController(R.id.purplleNavHost)
                    .navigate(R.id.brandFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.offerFragment-> {findNavController(R.id.purplleNavHost)
                    .navigate(R.id.offersFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.myOrdersFragment->{
                    if(SharedPrefManager.getInstance(this).isLoggedIn) {
                        findNavController(R.id.purplleNavHost)
                            .navigate(R.id.myOrdersFragment)
                        binding.myDrawer.closeDrawer(GravityCompat.START, true)
                    }
                    else
                    {
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.wishlistFragment-> {
                    if(SharedPrefManager.getInstance(this).isLoggedIn) {
                        findNavController(R.id.purplleNavHost)
                            .navigate(R.id.wishlistFragment)
                        binding.myDrawer.closeDrawer(GravityCompat.START, true)
                    }
                    else
                    {
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.myProfileFragment->{ if(SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myProfileFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                else
                {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                }
                R.id.customerSupportFragment-> {findNavController(R.id.purplleNavHost)
                    .navigate(R.id.customerSupportFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.privacyPolicyFragment-> {findNavController(R.id.purplleNavHost)
                .navigate(R.id.privacyPolicyFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.termsAndConditionsFragment-> {findNavController(R.id.purplleNavHost)
                    .navigate(R.id.termsAndConditionsFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.aboutAppFragment-> {findNavController(R.id.purplleNavHost)
                    .navigate(R.id.aboutAppFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
               R.id.newbies->
               {
                   findNavController(R.id.purplleNavHost)
                       .navigate(R.id.newbiesFragment)
                       binding.myDrawer.closeDrawer(GravityCompat.START, true)
               }
                R.id.feedback->
                {
//                    val emailIntent = Intent(
//                        Intent.ACTION_SENDTO, Uri.fromParts(
//                            "mailto", "crazybikkers@gmail.com", null
//                        )
//                    )
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Please describe your complaint here ..!")
//                    startActivity(Intent.createChooser(emailIntent, null))
            var mail = "crazybikkers@gmail.com"

//
//                    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$mail"))
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback of Affetta App")
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Please share your experience of using affetta app here in detail , Thank you !!")
////emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
//
////emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
//                    startActivity(Intent.createChooser(emailIntent, "Choose app for mail"))


                    val selectorIntent = Intent(Intent.ACTION_SENDTO)
                    selectorIntent.data = Uri.parse("mailto:")

                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("crazybikkers@gmail.com"))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "feedback for Affetta App")
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Please share your experience of using affetta app here in detail , Thank you !!")
                    emailIntent.selector = selectorIntent

                    startActivity(Intent.createChooser(emailIntent, "Send email..."))

                    binding.myDrawer.closeDrawer(GravityCompat.START, true)



                }


            }
            true
        }

    }

    @SuppressLint("RtlHardcoded")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.loginBtn -> {
                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                    Toast.makeText(this, "You are already logged in ", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.myProfileFragment->{
                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myProfileFragment)
                } else {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.myOrdersFragment->{
                findNavController(R.id.purplleNavHost)
                    .navigate(R.id.myOrdersFragment)
            }
//           // *********************************************************************
            //**********************************************************************
            // vvvvvvvviiiiiiiiiiimmmmmmmmmmpppppppppppppppppp    line
            // most imp line for up button and navigation drawer icon to work
            else -> {return super.onOptionsItemSelected(item)}



        }
        return true

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.purplleNavHost)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }


}











