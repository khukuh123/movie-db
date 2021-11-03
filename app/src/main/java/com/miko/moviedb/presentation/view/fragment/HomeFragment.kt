package com.miko.moviedb.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miko.moviedb.databinding.FragmentHomeBinding
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.ext.Mapper.toBannerModels
import com.miko.moviedb.ext.Mapper.toItemHomeModels
import com.miko.moviedb.presentation.view.adapter.BannerHomeAdapter
import com.miko.moviedb.presentation.view.adapter.MovieHomeAdapter

class HomeFragment : Fragment() {

    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentHomeBinding? = null
    private val bannerHomeAdapter by lazy {
        BannerHomeAdapter(Movie.generateLists().toBannerModels())
    }
    private val popularHomeAdapter by lazy {
        MovieHomeAdapter(Movie.generateLists().toItemHomeModels())
    }
    private val comingSoonHomeAdapter by lazy {
        MovieHomeAdapter(Movie.generateLists().toItemHomeModels())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            vpHomeBanner.adapter = bannerHomeAdapter
            rvHomePopularMovies.adapter = popularHomeAdapter
            rvHomeComingSoon.adapter = comingSoonHomeAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        bannerHomeAdapter.destroy()
        popularHomeAdapter.destroy()
        comingSoonHomeAdapter.destroy()

        super.onDestroyView()
    }
}