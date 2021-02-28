package com.liyaan.calculationtest.room

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liyaan.calculationtest.R

class MyListAdapter : ListAdapter<WordEntity, MyListAdapter.MyViewHolder> {
    private var mRoomViewModel:RoomViewModel? = null
    var id: Int = 0
    constructor(roomViewModel:RoomViewModel):super(object : DiffUtil.ItemCallback<WordEntity>(){
        override fun areItemsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
            return (oldItem.name.equals(newItem.name) && oldItem.password.equals(newItem.password)
                    && oldItem.passwordInvisibe==newItem.passwordInvisibe)
        }

    }){
        this.mRoomViewModel = roomViewModel
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber = itemView.findViewById<TextView>(R.id.textViewNumber)
        val tvEnglish = itemView.findViewById<TextView>(R.id.textViewEnglish)
        val tvCn = itemView.findViewById<TextView>(R.id.textViewCN)
        val item_constraint:ConstraintLayout = itemView.findViewById(R.id.item_switch)
        val mSwitch: Switch = itemView.findViewById(R.id.switch2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var itemView: View
        if (id == 0) {
            itemView = inflater.inflate(R.layout.item_cardview_2, parent, false)
        } else {
            itemView = inflater.inflate(R.layout.item_room, parent, false)
        }
        val holder = MyViewHolder(itemView)
        holder.itemView.setOnClickListener {
            val uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=${holder.tvEnglish.text}")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            holder.itemView.context.startActivity(intent)
        }
        holder.mSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val word:WordEntity = holder.itemView.getTag(R.id.word_for_view_holder) as WordEntity
            if (isChecked){
                holder.tvCn.visibility = View.GONE
                word.passwordInvisibe = true
                mRoomViewModel!!.updateWord(word)
            }else{
                holder.tvCn.visibility = View.VISIBLE
                word.passwordInvisibe = false
                mRoomViewModel!!.updateWord(word)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setTag(R.id.word_for_view_holder,getItem(position))
        holder.tvNumber.text = "${getItem(position).id}"
        holder.tvEnglish.text = getItem(position).name
        holder.tvCn.text = getItem(position).password

        if (getItem(position).passwordInvisibe){
            holder.tvCn.visibility = View.GONE
            holder.mSwitch.isChecked = getItem(position).passwordInvisibe
        }else{
            holder.tvCn.visibility = View.VISIBLE
            holder.mSwitch.isChecked = getItem(position).passwordInvisibe
        }
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.tvNumber.text = "${holder.adapterPosition+1}"
    }
}
