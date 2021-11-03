package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.R
import com.miko.moviedb.databinding.ItemFavoriteMovieBinding
import com.miko.moviedb.presentation.model.favorite.ItemFavoriteModel

class MovieFavoriteAdapter(private val data: ArrayList<ItemFavoriteModel>) :
    RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemFavoriteModel: ItemFavoriteModel) {
            with(binding) {
                tvItemFavoriteTitle.text = itemFavoriteModel.title
                tvItemFavoriteYear.text = itemFavoriteModel.year.toString()
                tvItemFavoriteGenres.text = itemFavoriteModel.genres.joinToString()

                Glide.with(root).load(R.drawable.ic_panorama).into(imgItemFavoriteMovie)
            }
        }
    }

    private var binding: ItemFavoriteMovieBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun destroy() {
        binding = null
    }
}