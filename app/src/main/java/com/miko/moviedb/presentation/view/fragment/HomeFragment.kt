package com.miko.moviedb.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.miko.moviedb.data.Resource
import com.miko.moviedb.databinding.FragmentHomeBinding
import com.miko.moviedb.ext.reusable.LinearLayoutItemDecoration
import com.miko.moviedb.ext.utils.Mapper.toBannerModels
import com.miko.moviedb.ext.utils.Mapper.toItemHomeModels
import com.miko.moviedb.presentation.view.adapter.BannerHomeAdapter
import com.miko.moviedb.presentation.view.adapter.MovieHomeAdapter
import com.miko.moviedb.presentation.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val bannerHomeAdapter by lazy {
        BannerHomeAdapter(arrayListOf())
    }
    private val popularHomeAdapter by lazy {
        MovieHomeAdapter(arrayListOf())
    }
    private val comingSoonHomeAdapter by lazy {
        MovieHomeAdapter(arrayListOf())
    }
    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()
        initAction()
    }

    private fun initAction() {
        var action: NavDirections
        bannerHomeAdapter.setOnItemClickCallback { model ->
            action = HomeFragmentDirections.actionHomePageToDetailActivity(model.id)
            view?.findNavController()?.navigate(action)
        }
        popularHomeAdapter.setOnItemClickCallback { model ->
            action = HomeFragmentDirections.actionHomePageToDetailActivity(model.id)
            view?.findNavController()?.navigate(action)
        }
        comingSoonHomeAdapter.setOnItemClickCallback { model ->
            action = HomeFragmentDirections.actionHomePageToDetailActivity(model.id)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun initObserver() {
        viewModel.getBanner().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    bannerHomeAdapter.setData(it.data!!.toBannerModels())
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {

                }
            }
        }
        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    popularHomeAdapter.setData(it.data!!.toItemHomeModels())
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        viewModel.getComingSoon(calendar.get(Calendar.YEAR) + 1).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    comingSoonHomeAdapter.setData(it.data!!.toItemHomeModels())
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            vpHomeBanner.adapter = bannerHomeAdapter
            rvHomePopularMovies.adapter = popularHomeAdapter
            rvHomePopularMovies.addItemDecoration(
                LinearLayoutItemDecoration(
                    16,
                    orientation = LinearLayout.HORIZONTAL
                )
            )
            rvHomeComingSoon.adapter = comingSoonHomeAdapter
            rvHomeComingSoon.addItemDecoration(
                LinearLayoutItemDecoration(
                    24,
                    orientation = LinearLayout.HORIZONTAL,
                    includeEdge = true
                )
            )
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