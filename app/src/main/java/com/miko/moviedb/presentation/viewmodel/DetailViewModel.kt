package com.miko.moviedb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.miko.moviedb.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getDetailMovie(id: Int) =
        movieUseCase.getDetailMovie(id).asLiveData()

    fun updateFavoriteMovie(added: Boolean, id: Int) =
        movieUseCase.updateMovieFavorite(added, id)

    fun checkFavoriteStatus(id: Int) =
        movieUseCase.checkFavoriteStatus(id).asLiveData()
}