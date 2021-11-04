package com.miko.moviedb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.miko.moviedb.domain.usecase.MovieUseCase

class FavoriteViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getFavoriteMovies() =
        movieUseCase.getFavoriteMovies().asLiveData()

    fun updateFavoriteMovie(added: Boolean, id: Int) =
        movieUseCase.updateMovieFavorite(added, id)
}