package com.liyaan.calculationtest.paging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.paging.pagRoom.Student

class PagingAdapter()
    : PagedListAdapter<Student, PagingAdapter.MyViewHolder>(object : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.studentNumber == newItem.studentNumber
    }

}) {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.item_paging_tv)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_paging_tv,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val student = getItem(position)
        if (student==null){
            holder.tv.text = "loading"
        }else{
            holder.tv.text = "${student!!.studentNumber}"
        }

    }
}