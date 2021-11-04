package com.miko.moviedb.data.source.local.room

import androidx.room.*
import com.miko.moviedb.data.source.local.entity.CastEntity
import com.miko.moviedb.data.source.local.entity.FavoriteMovieEntity
import com.miko.moviedb.data.source.local.entity.MovieEntity
import com.miko.moviedb.data.source.local.entity.MovieWithCasts
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE title LIKE :query")
    fun searchMovies(query: String): Flow<List<MovieWithCasts>>

    @Query("SELECT movie.* FROM movie, favorite WHERE movie.id = favorite.movieId AND movie.title LIKE :query")
    fun searchFavoriteMovies(query: String): Flow<List<MovieWithCasts>>

    @Query("SELECT movie.* FROM movie, favorite WHERE movie.id = favorite.movieId")
    fun getFavoriteMovies(): Flow<List<MovieWithCasts>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: Int): Flow<MovieEntity>

    @Query("UPDATE movie SET isFavorite = :added WHERE id = :id")
    fun updateMovie(id: Int, added: Boolean)

    @Transaction
    fun insertMoviesWithCasts(movies: List<MovieWithCasts>){
        movies.forEach { movieWithCast ->
            insertMovieEntity(movieWithCast.movie)
            insertCastEntities(movieWithCast.casts)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCastEntities(castEntities: List<CastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieEntity(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovies(favoriteMovies: List<FavoriteMovieEntity>)

    @Delete
    fun deleteFavoriteMovies(favoriteMovies: List<FavoriteMovieEntity>)

    @Transaction
    fun updateMovieFavorite(added: Boolean, favoriteMovie: FavoriteMovieEntity) {
        if (added) {
            insertFavoriteMovies(listOf(favoriteMovie))
        } else {
            deleteFavoriteMovies(listOf(favoriteMovie))
        }
        updateMovie(favoriteMovie.movieId, added)
    }

    @Query("SELECT movieId FROM favorite WHERE movieId = :id")
    fun checkFavoriteStatus(id: Int): Flow<List<FavoriteMovieEntity>>
}