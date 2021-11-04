package com.miko.moviedb.di

import androidx.room.Room
import com.miko.moviedb.BuildConfig
import com.miko.moviedb.data.MovieRepository
import com.miko.moviedb.data.source.local.LocalDataSource
import com.miko.moviedb.data.source.local.room.MovieDao
import com.miko.moviedb.data.source.local.room.MovieDatabase
import com.miko.moviedb.data.source.remote.RemoteDataSource
import com.miko.moviedb.data.source.remote.network.ApiService
import com.miko.moviedb.domain.repository.IMovieRepository
import com.miko.moviedb.domain.usecase.MovieInteractor
import com.miko.moviedb.domain.usecase.MovieUseCase
import com.miko.moviedb.ext.Const
import com.miko.moviedb.ext.utils.AppExecutors
import com.miko.moviedb.presentation.viewmodel.DetailViewModel
import com.miko.moviedb.presentation.viewmodel.FavoriteViewModel
import com.miko.moviedb.presentation.viewmodel.HomeViewModel
import com.miko.moviedb.presentation.viewmodel.PopularViewModel
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, Const.MOVIE_DATABASE
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val okHttpClient = OkHttpClient.Builder()
        if(BuildConfig.DEBUG){
            okHttpClient.addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        okHttpClient
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get(),
            get()
        )
    }
}

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor( get() ) }
}

val viewModelModule = module{
    viewModel { HomeViewModel( get() ) }
    viewModel { PopularViewModel( get() ) }
    viewModel { DetailViewModel( get() ) }
    viewModel { FavoriteViewModel( get() ) }
}