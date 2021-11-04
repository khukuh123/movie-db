package com.miko.moviedb.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<GenreModel>
)