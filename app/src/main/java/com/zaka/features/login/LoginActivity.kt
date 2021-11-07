package com.zaka.features.login

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zaka.R
import com.zaka.data.repositories.DeviceInfoRepository
import com.zaka.databinding.ActivityLoginBinding
import com.zaka.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

         navController = findNavController(R.id.nav_host_fragment_activity_login)
    //        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (navController?.currentDestination!!.id == R.id.loginFragment) {
            super.onBackPressed()
        } else {
            onNavigateUp()
        }
    }
    override fun onNavigateUp(): Boolean {
        return navController?.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (navController.currentDestination?.id != R.id.loginFragment) {
            return navController.navigateUp() || super.onSupportNavigateUp()
        } else {
            finish()
            return false
        }
    }

}