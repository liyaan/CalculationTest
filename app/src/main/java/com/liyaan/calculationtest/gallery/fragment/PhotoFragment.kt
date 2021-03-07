package com.liyaan.calculationtest.gallery.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.gallery.bean.PhotoItem
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shimmerLayoutPhoto.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }

        Glide.with(requireContext()).load(arguments?.getParcelable<PhotoItem>("PHOTO")?.fullUrl)
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
                        return false.also { shimmerLayoutPhoto.stopShimmerAnimation() }
                    }

                }).into(imageView5)
    }

}