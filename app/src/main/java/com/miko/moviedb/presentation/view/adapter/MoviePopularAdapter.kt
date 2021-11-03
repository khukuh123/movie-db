package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.R
import com.miko.moviedb.databinding.ItemPopularMovieBinding
import com.miko.moviedb.presentation.model.popular.ItemPopularModel

class MoviePopularAdapter(private val data: ArrayList<ItemPopularModel>) :
    RecyclerView.Adapter<MoviePopularAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemPopularMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemPopularModel: ItemPopularModel) {
            with(binding) {
                tvItemPopularTitle.text = itemPopularModel.title
                tvItemPopularGenre.text = itemPopularModel.genre
                tvItemPopularCast.text = itemPopularModel.casts.joinToString()
                Glide.with(root).load(R.drawable.ic_panorama).into(imgItemPopularMovie)
            }
        }
    }

    private var binding: ItemPopularMovieBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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