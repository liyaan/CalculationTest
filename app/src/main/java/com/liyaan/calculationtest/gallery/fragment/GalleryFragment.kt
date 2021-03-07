package com.liyaan.calculationtest.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.gallery.adapter.GalleryAdapter
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
                    galleryViewModel.getData()
                },1000)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val galleryAdapter = GalleryAdapter()
        recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        galleryViewModel = ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(GalleryViewModel::class.java)
        galleryViewModel.photoListLive.observe(requireActivity(), Observer {
            galleryAdapter.submitList(it)
            swipeLayoutGallery.isRefreshing = false
        })
        galleryViewModel.photoListLive.value?:galleryViewModel.getData()

        swipeLayoutGallery.setOnRefreshListener {
            galleryViewModel.getData()
        }
    }
}