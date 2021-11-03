package com.miko.moviedb.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.miko.moviedb.R
import com.miko.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initUI()
    }

    private fun initUI() {
        binding?.let { b->
            navHostFragment = (supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment)
            navController = navHostFragment.navController
            b.botnavMain.setupWithNavController(navController)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}