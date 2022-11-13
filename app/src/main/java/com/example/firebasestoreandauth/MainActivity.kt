package com.example.firebasestoreandauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasestoreandauth.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    lateinit var appbarc : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nhf = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
        val navController = nhf.navController
        appbarc = AppBarConfiguration(setOf(R.id.profileFragment, R.id.friendsFragment, R.id.postFragment))
        setupActionBarWithNavController(nhf.navController, appbarc)

        binding.bottomNavigationView.setupWithNavController(navController)
        //navigationItemSelect()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.my_nav_host)
        return navController.navigateUp(appbarc)
                || super.onSupportNavigateUp()
    }

    /*
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()
    }

    private fun navigationItemSelect() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.friendsFragment -> replaceFragment(FriendsFragment())
                    R.id.postFragment -> replaceFragment(PostFragment())
                    R.id.profileFragment -> replaceFragment(ProfileFragment())
                }
                true
            }
            selectedItemId = R.id.postFragment
        }
    }

     */
}