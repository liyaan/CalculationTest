package com.liyaan.calculationtest.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.gallery.adapter.GalleryAdapter
import com.liyaan.calculationtest.gallery.model.DATA_STATUS_NETWORK_ERROR
import com.liyaan.calculationtest.gallery.model.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {
    lateinit var  galleryViewModel:GalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.swipe->{
                swipeLayoutGallery.isRefreshing = true
                Handler().postDelayed(Runnable {
                    galleryViewModel.resetQuery()
                },1000)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        galleryViewModel = ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(GalleryViewModel::class.java)
        val galleryAdapter = GalleryAdapter(galleryViewModel)
        recyclerView.apply {
            adapter = galleryAdapter
            //layoutManager = GridLayoutManager(requireContext(),2)
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }


        galleryViewModel.photoListLive.observe(requireActivity(), Observer {
            if (galleryViewModel.needToScrollTop){
                recyclerView.scrollToPosition(0)
                galleryViewModel.needToScrollTop = false
            }
            galleryAdapter.submitList(it)
            swipeLayoutGallery.isRefreshing = false

        })
        galleryViewModel.dataStatusLive.observe(requireActivity(), Observer {
            Log.i("aaaaaaa","it=$it")
            galleryAdapter.footerViewStatus = it
            galleryAdapter.notifyItemChanged(galleryAdapter.itemCount-1)
            if (it == DATA_STATUS_NETWORK_ERROR)swipeLayoutGallery.isRefreshing = false
        })
//        galleryViewModel.photoListLive.value?:galleryViewModel.resetQuery()

        swipeLayoutGallery.setOnRefreshListener {
            galleryViewModel.resetQuery()
        }
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy<0)return
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val intArray = IntArray(2)
                layoutManager.findLastVisibleItemPositions(intArray)
                if (intArray[0]==galleryAdapter.itemCount-1){
                    galleryViewModel.getData()
                }
            }
        })
    }
}