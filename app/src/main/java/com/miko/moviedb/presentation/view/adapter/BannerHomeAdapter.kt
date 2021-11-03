package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.R
import com.miko.moviedb.databinding.ItemHomeBannerBinding
import com.miko.moviedb.presentation.model.home.BannerModel

class BannerHomeAdapter(private val data: ArrayList<BannerModel>): RecyclerView.Adapter<BannerHomeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemHomeBannerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bannerModel: BannerModel){
            with(binding){
                Glide.with(root).load(R.drawable.ic_panorama).into(imgItemHomeBanner)
            }
        }
    }

    private var binding: ItemHomeBannerBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun destroy(){
        binding = null
    }
}