package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.databinding.ItemPopularMovieBinding
import com.miko.moviedb.ext.Const
import com.miko.moviedb.presentation.model.popular.ItemPopularModel

class MoviePopularAdapter(private val data: ArrayList<ItemPopularModel>) :
    RecyclerView.Adapter<MoviePopularAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemPopularMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemPopularModel: ItemPopularModel) {
            with(binding) {
                tvItemPopularTitle.text = itemPopularModel.title
                tvItemPopularGenre.text = itemPopularModel.genre
                tvItemPopularOverview.text = itemPopularModel.overview
                Glide.with(root).load("${Const.BASE_IMG_URL}${itemPopularModel.poster}").into(imgItemPopularMovie)

                imgItemPopularMovie.setOnClickListener {
                    onItemClickCallback.onItemClicked(itemPopularModel)
                }
            }
        }
    }

    private var binding: ItemPopularMovieBinding? = null
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: ArrayList<ItemPopularModel>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun destroy() {
        binding = null
    }

    fun interface OnItemClickCallback{
        fun onItemClicked(itemPopularModel: ItemPopularModel)
    }
}