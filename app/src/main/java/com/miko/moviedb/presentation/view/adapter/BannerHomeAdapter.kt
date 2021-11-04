package com.miko.moviedb.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miko.moviedb.R
import com.miko.moviedb.databinding.ItemHomeBannerBinding
import com.miko.moviedb.ext.Const
import com.miko.moviedb.presentation.model.home.BannerModel
import com.miko.moviedb.presentation.model.home.ItemHomeModel

class BannerHomeAdapter(private val data: ArrayList<BannerModel>): RecyclerView.Adapter<BannerHomeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemHomeBannerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bannerModel: BannerModel){
            with(binding){
                Glide.with(root).load("${Const.BASE_IMG_URL}${bannerModel.poster}").into(imgItemHomeBanner)

                imgItemHomeBanner.setOnClickListener {
                    onItemClickCallback.onItemClicked(bannerModel)
                }
            }
        }
    }

    private var binding: ItemHomeBannerBinding? = null
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: ArrayList<BannerModel>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun destroy(){
        binding = null
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun interface OnItemClickCallback{
        fun onItemClicked(bannerModel: BannerModel)
    }
}