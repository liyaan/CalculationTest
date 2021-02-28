package com.liyaan.calculationtest.room

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.liyaan.calculationtest.R
import kotlinx.android.synthetic.main.fragment_words.*

class WordsFragment : Fragment() {
    var mRoomViewModel:RoomViewModel? = null
    var mRecyclerView: RecyclerView? = null
    var mAdapter:MyListAdapter? = null
    var mFloatingActionButton:FloatingActionButton? = null
    var filteredWord: LiveData<MutableList<WordEntity>>? = null
    var words:MutableList<WordEntity>? = null
    private var boolean:Boolean = false
    init {
        setHasOptionsMenu(true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRoomViewModel = ViewModelProviders.of(requireActivity()).get(RoomViewModel::class.java)
        mRecyclerView = requireActivity().findViewById(R.id.recyclerview)
        words = ArrayList()
        mAdapter = MyListAdapter(mRoomViewModel!!)
        mRecyclerView!!.layoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.itemAnimator = object : DefaultItemAnimator(){
            override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
                super.onAnimationFinished(viewHolder)
                val layoutManager:LinearLayoutManager = mRecyclerView!!.layoutManager as LinearLayoutManager
                if (layoutManager!=null){
                    if (mAdapter!!.itemCount>1){
                        val fristPosintion:Int = layoutManager.findFirstVisibleItemPosition()
                        val lastPosintion:Int = layoutManager.findLastVisibleItemPosition()
                        val i = fristPosintion
                        for (i in 0 .. lastPosintion){
                            val holder:MyListAdapter.MyViewHolder? =
                                    mRecyclerView!!.findViewHolderForAdapterPosition(i)
                                            as MyListAdapter.MyViewHolder?
                            if (holder!=null){
                                holder!!.tvNumber.text = "${i+1}"
                            }

                        }
                    }

                }
            }
        }
        filteredWord = mRoomViewModel!!.getAllWords()
        filteredWord!!.observe(viewLifecycleOwner, Observer<MutableList<WordEntity>> { list ->
            val tempSize = mAdapter!!.itemCount
            words!!.addAll(list)
            if (tempSize!=list.size){
                if (tempSize<list.size){
//                    if (!boolean){
//                        mRecyclerView!!.smoothScrollBy(0,200)
//                    }else{
//                        boolean = false
//                    }
                }
                mAdapter!!.submitList(list)
            }
        })

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.START or  ItemTouchHelper.END){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val wordEntity:WordEntity =words!!.get(viewHolder.adapterPosition)
                mRoomViewModel!!.deleteWord(wordEntity)
                Snackbar.make(main_coor_layout,"删除了一个词汇",Snackbar.LENGTH_SHORT)
                        .setAction("撤销") {
                            boolean = true
                            mRoomViewModel!!.insertWord(wordEntity)
                        }.show()

            }

        }).attachToRecyclerView(mRecyclerView)

        mFloatingActionButton = requireActivity().findViewById(R.id.floatingActionButton)
        mFloatingActionButton!!.setOnClickListener {
            val nav:NavController = Navigation.findNavController(it)
            nav.navigate(R.id.action_wordsFragment_to_addFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_value,menu)
        val searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val maxW:Int = (searchView.maxWidth*0.6).toInt()
        searchView.maxWidth = 900
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val pattern = newText!!.trim()
                filteredWord!!.removeObservers(requireActivity())
                filteredWord = mRoomViewModel!!.findWordsWithPattern(pattern)
                filteredWord!!.observe(viewLifecycleOwner, Observer<MutableList<WordEntity>> { t ->
                    val tempSize = mAdapter!!.itemCount

                    if (tempSize!=t!!.size){
                        mAdapter!!.submitList(t!!)
                    }
                })
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clearData->{
                val builder:AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("清空数据")
                builder.setPositiveButton("确定") { dialog, which -> mRoomViewModel!!.deleteAllWord() }
                builder.setNegativeButton("取消") { dialog, which -> }
                builder.create()
                builder.show()
            }
            R.id.switchViewType->{
                Toast.makeText(requireActivity(),"jianshezhizhong",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}