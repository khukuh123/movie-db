package com.miko.moviedb.presentation.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.miko.moviedb.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initUI()
    }

    private fun initUI(){
        binding?.let { b ->
            setSupportActionBar(b.tbDetail)
            supportActionBar?.title = ""
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            var isCollapsed = false
            var scrollRange = -1
            b.ablDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    b.ctbDetail.title = "Now You See Me 2"
                    isCollapsed = true
                }else if(isCollapsed) {
                    b.ctbDetail.title = ""
                    isCollapsed = false
                }
            })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}