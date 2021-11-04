package com.miko.moviedb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.miko.moviedb.data.Resource
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.domain.usecase.MovieUseCase

class PopularViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getPopularMovies(): LiveData<Resource<out ArrayList<Movie>>> =
        movieUseCase.getPopularMovies().asLiveData()

}