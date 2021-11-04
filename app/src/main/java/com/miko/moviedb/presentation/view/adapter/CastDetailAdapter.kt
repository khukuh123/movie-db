package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.databinding.ItemDetailCastBinding
import com.miko.moviedb.ext.Const
import com.miko.moviedb.presentation.model.detail.ItemCastModel

class CastDetailAdapter(private val data: ArrayList<ItemCastModel>) : RecyclerView.Adapter<CastDetailAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemDetailCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemCastModel: ItemCastModel){
            with(binding){
                Glide.with(binding.root).load("${Const.BASE_IMG_URL}${itemCastModel.avatar}").into(civItemDetailCast)

                tvItemDetailCast.text = itemCastModel.name
            }
        }
    }

    private var binding: ItemDetailCastBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDetailCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: ArrayList<ItemCastModel>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun destroy(){
        binding = null
    }
}