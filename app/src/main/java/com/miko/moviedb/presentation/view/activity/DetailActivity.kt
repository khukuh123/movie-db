package com.miko.moviedb.presentation.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.miko.moviedb.R
import com.miko.moviedb.data.Resource
import com.miko.moviedb.databinding.ActivityDetailBinding
import com.miko.moviedb.ext.Const
import com.miko.moviedb.ext.reusable.LinearLayoutItemDecoration
import com.miko.moviedb.ext.utils.Mapper.toDetailModel
import com.miko.moviedb.presentation.model.detail.DetailModel
import com.miko.moviedb.presentation.view.adapter.CastDetailAdapter
import com.miko.moviedb.presentation.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private var binding: ActivityDetailBinding? = null
    private var movieId: Int = -1
    private var movieTitle: String? = ""
    private var movieIsFavorite: Boolean = false
    private val args: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()
    private val castDetailAdapter: CastDetailAdapter by lazy{
        CastDetailAdapter(arrayListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initBundle()
        initUI()
        initAction()
        initObserver()
    }

    private fun initAction() {
        binding?.apply {
            btnDetailAddToFavorite.setOnClickListener {
                if(movieId > 0){
                    movieIsFavorite = !movieIsFavorite
                    viewModel.updateFavoriteMovie(movieIsFavorite, movieId)
                    updateFavoriteState(movieIsFavorite)
                }
            }
        }
    }

    private fun updateFavoriteState(added: Boolean) {
        binding?.apply {
            if(added){
                btnDetailAddToFavorite.apply {
                    setIconResource(R.drawable.ic_favorite)
                    text = "Added!"
                }
            }else{
                btnDetailAddToFavorite.apply {
                    setIconResource(R.drawable.ic_add)
                    text = getString(R.string.action_add_to_favorite)
                }
            }
            btnDetailAddToFavorite.iconSize = 24
        }
    }

    private fun initObserver() {
        viewModel.getDetailMovie(movieId).observe(this){
            when (it) {
                is Resource.Success -> {
                    setData(it.data!!.toDetailModel())
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
        viewModel.checkFavoriteStatus(movieId).observe(this){
            updateFavoriteState(it.size > 0)
        }
    }

    private fun setData(detailModel: DetailModel) {
        binding?.apply {
            tvDetailTitle.text = detailModel.title
            val duration = "${detailModel.duration} m"
            tvDetailDuration.text = duration
            val genres = detailModel.genres.joinToString(separator = "•")
            tvDetailGenre.text = genres
            tvDetailSynopsis.text = detailModel.synopsis

            Glide.with(this@DetailActivity).load("${Const.BASE_IMG_URL}${detailModel.backDrop}").into(imgDetailPoster)

            castDetailAdapter.setData(detailModel.casts)

            movieTitle = detailModel.title
            movieIsFavorite = detailModel.isFavorite
        }
    }

    private fun initBundle() {
        movieId = args.movieId
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
                    if(movieTitle != null){
                        b.ctbDetail.title = movieTitle
                        isCollapsed = true
                    }
                }else if(isCollapsed) {
                    b.ctbDetail.title = ""
                    isCollapsed = false
                }
            })
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvDetailCast.adapter = castDetailAdapter
            rvDetailCast.addItemDecoration(LinearLayoutItemDecoration(24, orientation = LinearLayout.HORIZONTAL))
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
        castDetailAdapter.destroy()
        super.onDestroy()
    }

}