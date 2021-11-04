package com.miko.moviedb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.miko.moviedb.data.Resource
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getBanner(): LiveData<Resource<out ArrayList<Movie>>> =
        movieUseCase.getBanner().asLiveData()

    fun getPopularMovies(): LiveData<Resource<out ArrayList<Movie>>> =
        movieUseCase.getPopularMovies().asLiveData()

    fun getComingSoon(year: Int): LiveData<Resource<out ArrayList<Movie>>> =
        movieUseCase.getComingSoon(year).asLiveData()
}