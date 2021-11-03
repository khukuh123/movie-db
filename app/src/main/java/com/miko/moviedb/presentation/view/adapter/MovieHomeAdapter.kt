package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.R
import com.miko.moviedb.databinding.ItemHomeMovieBinding
import com.miko.moviedb.presentation.model.home.ItemHomeModel

class MovieHomeAdapter(private val data: ArrayList<ItemHomeModel>) :
    RecyclerView.Adapter<MovieHomeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemHomeMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHomeModel: ItemHomeModel) {
            with(binding) {
                Glide.with(root).load(R.drawable.ic_panorama).into(imgItemHomeMovie)
            }
        }
    }

    private var binding: ItemHomeMovieBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHomeMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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