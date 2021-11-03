package com.miko.moviedb.presentation.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.miko.moviedb.R
import com.miko.moviedb.databinding.FragmentFavoriteBinding
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.ext.Mapper.toItemFavoriteModels
import com.miko.moviedb.ext.initTextChanges
import com.miko.moviedb.presentation.view.adapter.MovieFavoriteAdapter
import kotlinx.coroutines.flow.*

class FavoriteFragment : Fragment() {

    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentFavoriteBinding? = null
    private var queryFlow: Flow<String?>? = null
    private val movieFavoriteAdapter by lazy {
        MovieFavoriteAdapter(Movie.generateLists().toItemFavoriteModels())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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