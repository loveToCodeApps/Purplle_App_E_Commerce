package com.example.purpleapp

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.purpleapp.api.MemoBroadcast
import com.example.purpleapp.api.SharedPrefManager
import com.example.purpleapp.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var menu:Menu
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // action bar title changed
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//        if (isDarkTheme(this))
//        {
//            setTheme(R.style.Theme_PurpleApp)
//        }
        //myAlarm()

      //09-02-2023  code for notification
        NotificationChannels()




        val calendar = Calendar.getInstance()
        val currentHourIn24Format: Int =calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinuteIn24Format: Int =calendar.get(Calendar.MINUTE)+1
        calendar[Calendar.HOUR_OF_DAY] = currentHourIn24Format
        calendar[Calendar.MINUTE] = currentMinuteIn24Format
        calendar[Calendar.SECOND] = 0

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = Intent(this@MainActivity, MemoBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            120000L,
            pendingIntent
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------
        drawerLayout = binding.myDrawer

        val navController = this.findNavController(R.id.purplleNavHost)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.myNavView, navController)
        binding.bottomNavigationView.setupWithNavController(navController)

        //---------------------------------------------------------------------------------
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val user = SharedPrefManager.getInstance(this).user
       //     binding.textView87.text = user.firstName.toString()

            // show logout option from drawer only if logged in
            val nav_Menu: Menu = binding.myNavView.menu
            nav_Menu.findItem(R.id.logout).setVisible(true);

            val headerView = binding.myNavView.getHeaderView(0)
            val first_name = headerView.findViewById<TextView>(R.id.textView10)
            val last_name = headerView.findViewById<TextView>(R.id.username)
            val first = SharedPrefManager.getInstance(this).user.firstName + " "
            val last = SharedPrefManager.getInstance(this).user.lastName
            first_name.text = first
            last_name.text = last
        }
        else
        {
            val nav_Menu: Menu = binding.myNavView.menu
            nav_Menu.findItem(R.id.logout).setVisible(false);
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

        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestinationId) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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
                        finish()
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
                        Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                R.id.myProfileFragment->{ if(SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myProfileFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                else
                {
                    Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                }
                R.id.customerSupportFragment-> {findNavController(R.id.purplleNavHost)
                    .navigate(R.id.customerSupportFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.privacyPolicyFragment-> {
                    findNavController(R.id.purplleNavHost)
                .navigate(R.id.privacyPolicyFragment)

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
                R.id.logout->
                {
                    SharedPrefManager.getInstance(applicationContext).logout()
                    Toast.makeText(this, "Logout successfully !!", Toast.LENGTH_SHORT).show();
                    finish()

                }

                R.id.feedback->
                {
                    if(SharedPrefManager.getInstance(this).isLoggedIn) {
                        var mail = "@gmail.com"

                        val selectorIntent = Intent(Intent.ACTION_SENDTO)
                        selectorIntent.data = Uri.parse("mailto:")

                        val emailIntent = Intent(Intent.ACTION_SEND)
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("support@affetta.com"))
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "feedback for Affetta App")
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please share your experience of using affetta app here in detail , Thank you !!")
                        emailIntent.selector = selectorIntent
                        startActivity(Intent.createChooser(emailIntent, "Send email..."))
                        binding.myDrawer.closeDrawer(GravityCompat.START, true)

                    }
                    else
                    {
                        Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
            }

                else -> {
                    return@setNavigationItemSelectedListener super.onOptionsItemSelected(it)
                }


            }
            true
        }

    }

    private fun NotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "PASTICCINO"
            val description = "PASTICCINO`S CHANNEL"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notification", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun myAlarm() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.set(Calendar.MINUTE, 32)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.getTime().compareTo(Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(this.applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            60000L,
            pendingIntent
        )
    }

    @SuppressLint("RtlHardcoded")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.loginBtn -> {
                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                 SharedPrefManager.getInstance(applicationContext).logout()
                    Toast.makeText(this, "Logout successfully !!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "you can login now !!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
            R.id.myProfileFragment->{
                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myProfileFragment)
                } else {
                    Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.myOrdersFragment->{

                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myOrdersFragment)
                } else {
                    Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }

            R.id.wishlistFragment->{

                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.wishlistFragment)
                } else {
                    Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
            R.id.myCartFragment->{

                if (SharedPrefManager.getInstance(this).isLoggedIn) {
                    findNavController(R.id.purplleNavHost)
                        .navigate(R.id.myCartFragment)
                } else {
                    Toast.makeText(this, "you will first need to login !!", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }
//           // *********************************************************************
            //**********************************************************************
            // vvvvvvvviiiiiiiiiiimmmmmmmmmmpppppppppppppppppp    line


            // most imp line for up button and navigation drawer icon to work

            else -> {
                return super.onOptionsItemSelected(item)
            }



        }
        return true

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.purplleNavHost)
        return NavigationUI.navigateUp(navController, drawerLayout)


    }




    fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
    }














