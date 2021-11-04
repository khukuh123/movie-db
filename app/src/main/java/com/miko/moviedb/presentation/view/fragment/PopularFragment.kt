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
import com.miko.moviedb.data.Resource
import com.miko.moviedb.databinding.FragmentPopularBinding
import com.miko.moviedb.ext.reusable.GridLayoutItemDecoration
import com.miko.moviedb.ext.utils.Mapper.toItemPopularModels
import com.miko.moviedb.ext.utils.initTextChanges
import com.miko.moviedb.presentation.view.adapter.MoviePopularAdapter
import com.miko.moviedb.presentation.viewmodel.PopularViewModel
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private var binding: FragmentPopularBinding? = null
    private var queryFlow: Flow<String?>? = null
    private val moviePopularAdapter by lazy {
        MoviePopularAdapter(arrayListOf())
    }
    private val popularViewModel: PopularViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularBinding.inflate(inflater)

        setHasOptionsMenu(true)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
        initObserver()
    }

    private fun initAction() {
        moviePopularAdapter.setOnItemClickCallback{ model ->
            val action = PopularFragmentDirections.actionPopularPageToDetailActivity(model.id)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun initObserver() {
        popularViewModel.getPopularMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    moviePopularAdapter.setData(it.data!!.toItemPopularModels())
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun initUI() {
        binding?.apply {
            (requireActivity() as AppCompatActivity).apply {
                setSupportActionBar(tbPopular)
                supportActionBar?.title = ""
            }
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvPopular.adapter = moviePopularAdapter
            rvPopular.addItemDecoration(GridLayoutItemDecoration(2, 24, false))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.popular_menu, menu)

        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_popular_search).actionView as SearchView).apply {
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
        moviePopularAdapter.destroy()
        super.onDestroyView()
    }
}