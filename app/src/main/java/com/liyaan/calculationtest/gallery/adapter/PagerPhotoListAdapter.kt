package com.liyaan.calculationtest.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.gallery.bean.PhotoItem
import kotlinx.android.synthetic.main.pager_photo_view.view.*

class PagerPhotoListAdapter: ListAdapter<PhotoItem, MyPhotoHolder>(DiffCallBack) {

    object DiffCallBack:DiffUtil.ItemCallback<PhotoItem>(){
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pager_photo_view,parent,false)
        return MyPhotoHolder(view)
    }

    override fun onBindViewHolder(holder: MyPhotoHolder, position: Int) {
        Glide.with(holder.itemView).load(getItem(position).previewUrl)
                .placeholder(R.drawable.ic_baseline_insert_photo_24).into(holder.itemView.pagerPhoto)
    }
}

class MyPhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)