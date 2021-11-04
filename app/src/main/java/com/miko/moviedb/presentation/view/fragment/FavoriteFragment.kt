package com.miko.moviedb.presentation.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.miko.moviedb.R
import com.miko.moviedb.databinding.FragmentFavoriteBinding
import com.miko.moviedb.ext.reusable.LinearLayoutItemDecoration
import com.miko.moviedb.ext.utils.Mapper.toItemFavoriteModels
import com.miko.moviedb.ext.utils.initTextChanges
import com.miko.moviedb.presentation.model.favorite.ItemFavoriteModel
import com.miko.moviedb.presentation.view.adapter.MovieFavoriteAdapter
import com.miko.moviedb.presentation.viewmodel.FavoriteViewModel
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private var queryFlow: Flow<String?>? = null
    private val movieFavoriteAdapter by lazy {
        MovieFavoriteAdapter(arrayListOf())
    }
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)

        setHasOptionsMenu(true)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()
        initAction()
    }

    private fun initAction() {
        movieFavoriteAdapter.setOnClickCallback(object : MovieFavoriteAdapter.OnClickCallback {
            override fun onFavoriteClicked(itemFavoriteModel: ItemFavoriteModel, position: Int) {
                itemFavoriteModel.isFavorite = !itemFavoriteModel.isFavorite
                favoriteViewModel.updateFavoriteMovie(
                    itemFavoriteModel.isFavorite,
                    itemFavoriteModel.id
                )
                movieFavoriteAdapter.removeData(itemFavoriteModel, position)
            }

            override fun onItemClicked(itemFavoriteModel: ItemFavoriteModel) {
                val action =
                    FavoriteFragmentDirections.actionFavoritePageToDetailActivity(itemFavoriteModel.id)
                view?.findNavController()?.navigate(action)
            }
        })
    }

    private fun initObserver() {
        favoriteViewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
            movieFavoriteAdapter.setData(it.toItemFavoriteModels())
        }
    }

    private fun initUI() {
        binding?.apply {
            (requireActivity() as AppCompatActivity).apply {
                setSupportActionBar(tbFavorite)
                supportActionBar?.title = ""
            }
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvFavorite.adapter = movieFavoriteAdapter
            rvFavorite.addItemDecoration(LinearLayoutItemDecoration(16, includeEdge = true))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_menu, menu)

        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_favorite_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryHint = getString(R.string.action_search)

            queryFlow = initTextChanges().also { f ->
                f.debounce(300).distinctUntilChanged().onEach { query ->
                    if (query != null) {
                        if(query.isNotEmpty()){
                            Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.launchIn(lifecycleScope)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        binding = null
        movieFavoriteAdapter.destroy()
        super.onDestroyView()
    }
}