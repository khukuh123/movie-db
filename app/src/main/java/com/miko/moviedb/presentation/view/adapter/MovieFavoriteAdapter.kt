package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.databinding.ItemFavoriteMovieBinding
import com.miko.moviedb.ext.Const
import com.miko.moviedb.presentation.model.favorite.ItemFavoriteModel

class MovieFavoriteAdapter(private val data: ArrayList<ItemFavoriteModel>) :
    RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemFavoriteModel: ItemFavoriteModel, position: Int) {
            with(binding) {
                tvItemFavoriteTitle.text = itemFavoriteModel.title
                tvItemFavoriteYear.text = itemFavoriteModel.year
                tvItemFavoriteGenres.text = itemFavoriteModel.genres.joinToString()

                Glide.with(root).load("${Const.BASE_IMG_URL}${itemFavoriteModel.poster}").into(imgItemFavoriteMovie)

                btnItemFavoriteAddToFavorite.setOnClickListener {
                    onClickCallback.onFavoriteClicked(itemFavoriteModel, position)
                }
                root.setOnClickListener {
                    onClickCallback.onItemClicked(itemFavoriteModel)
                }
            }
        }
    }

    private var binding: ItemFavoriteMovieBinding? = null
    private lateinit var onClickCallback: OnClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: ArrayList<ItemFavoriteModel>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun removeData(data: ItemFavoriteModel,position: Int){
        this.data.remove(data)
        notifyItemRemoved(position)
    }

    fun setOnClickCallback(onClickCallback: OnClickCallback){
        this.onClickCallback = onClickCallback
    }

    fun destroy() {
        binding = null
    }

    interface OnClickCallback{
        fun onFavoriteClicked(itemFavoriteModel: ItemFavoriteModel, position: Int)

        fun onItemClicked(itemFavoriteModel: ItemFavoriteModel)
    }
}