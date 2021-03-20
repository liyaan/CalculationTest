package com.liyaan.calculationtest.gallery.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.gallery.bean.PhotoItem
import com.liyaan.calculationtest.gallery.model.DATA_STATUS_CAN_LOAD_MORE
import com.liyaan.calculationtest.gallery.model.DATA_STATUS_NETWORK_ERROR
import com.liyaan.calculationtest.gallery.model.DATA_STATUS_NO_MORE
import com.liyaan.calculationtest.gallery.model.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_pager_photo.view.*
import kotlinx.android.synthetic.main.gallery_footview.view.*
import kotlinx.android.synthetic.main.item_grallery_cell.view.*

class GalleryAdapter(val alleryViewModel: GalleryViewModel):ListAdapter<PhotoItem, MyViewHolder>(DIFFCALLBACK) {
    companion object{
        const val NORMAL_VIEW_TYPE = 0
        const val FOOTER_VIEW_TYPE = 1
    }
    var footerViewStatus = DATA_STATUS_CAN_LOAD_MORE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder:MyViewHolder
        if (viewType== NORMAL_VIEW_TYPE){
            holder = MyViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_grallery_cell,parent,false))
            holder.itemView.setOnClickListener {
                Bundle().apply {
//                putParcelable("PHOTO",getItem(holder.adapterPosition))
//                holder.itemView.findNavController()
//                        .navigate(R.id.action_galleryFragment_to_photoFragment,this)
                    putParcelableArrayList("PHOTO_LIST", ArrayList(currentList))
                    putInt("PHOTO_POSITION",holder.adapterPosition)
                    holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_pagerPhotoFragment,
                            this)
                }
            }
        }else{
            holder = MyViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.gallery_footview,parent,false).also {
                        (it.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                        it.setOnClickListener {itemView->
                            itemView.progressBar.visibility = View.VISIBLE
                            itemView.textView16.text = "正在加载"
                            alleryViewModel.getData()
                        }
                    })
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==itemCount-1) FOOTER_VIEW_TYPE else NORMAL_VIEW_TYPE
    }
    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position==itemCount-1){
            with(holder.itemView){
                when(footerViewStatus){
                    DATA_STATUS_CAN_LOAD_MORE->{
                        progressBar.visibility = View.VISIBLE
                        textView16.text = "正在加载"
                        isClickable = false
                    }
                    DATA_STATUS_NO_MORE->{
                        Log.i("aaaaaa","footerViewStatus=$footerViewStatus")
                        progressBar.visibility = View.GONE
                        textView16.text = "全部加载完毕，滚动到底部"
                        isClickable = false
                    }
                    DATA_STATUS_NETWORK_ERROR->{
                        progressBar.visibility = View.GONE
                        textView16.text = "网络故障，稍后重试"
                        isClickable = true
                    }
                }
            }
            return
        }
        val photoItem = getItem(position)
        with(holder.itemView){
            shimmerLayoutCell.apply {
                setShimmerColor(0x55FFFFFF)
                setShimmerAngle(0)
                startShimmerAnimation()
            }
            imageView.layoutParams.height = getItem(position).photoHeight
            textViewUser.text = photoItem.photoUser
            textViewlike.text = photoItem.photoLikes.toString()
            textViewFactory.text = photoItem.photoFavorites.toString()
        }
        Glide.with(holder.itemView).load(getItem(position).previewUrl)
                .placeholder(R.drawable.ic_baseline_insert_photo_24)
                .listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?,
                                                 target: Target<Drawable>?, dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        return false.also { holder.itemView.shimmerLayoutCell?.stopShimmerAnimation() }
                    }

                })
                .into(holder.itemView.imageView)
    }

    object DIFFCALLBACK:DiffUtil.ItemCallback<PhotoItem>(){
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }

    }

}
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)