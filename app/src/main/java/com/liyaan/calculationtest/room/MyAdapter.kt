package com.liyaan.calculationtest.room

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.liyaan.calculationtest.R

class MyAdapter(roomViewModel:RoomViewModel): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var list: MutableList<WordEntity> = ArrayList()
    var id: Int = 0
    val mRoomViewModel:RoomViewModel = roomViewModel
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
                mRoomViewModel.updateWord(word)
            }else{
                holder.tvCn.visibility = View.VISIBLE
                word.passwordInvisibe = false
                mRoomViewModel.updateWord(word)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setTag(R.id.word_for_view_holder,list.get(position))
        holder.tvNumber.text = "${list.get(position).id}"
        holder.tvEnglish.text = list.get(position).name
        holder.tvCn.text = list.get(position).password
        holder.item_constraint.setOnClickListener {
            val uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=${list.get(position).name}")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            holder.itemView.context.startActivity(intent)
        }
        if (list.get(position).passwordInvisibe){
            holder.tvCn.visibility = View.GONE
            holder.mSwitch.isChecked = list.get(position).passwordInvisibe
        }else{
            holder.tvCn.visibility = View.VISIBLE
            holder.mSwitch.isChecked = list.get(position).passwordInvisibe
        }


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}
