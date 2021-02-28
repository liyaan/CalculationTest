package com.liyaan.calculationtest.buttomFragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.InflateException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.liyaan.calculationtest.R
import java.util.*


class ThridFragment : Fragment() {
    private var imgView: ImageView? = null
    companion object {
        fun newInstance() = ThridFragment()
    }

    private lateinit var viewModel: ThridViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.thrid_fragment, container, false)
        imgView = view.findViewById(R.id.imageView)
        return view
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ThridViewModel::class.java)
        imgView!!.x= imgView!!.x+viewModel.dx
        val objectTranslation = ObjectAnimator.ofFloat(imgView,"x",0f,0f)
        objectTranslation.duration = 500
        imgView!!.setOnClickListener {
            if (!objectTranslation.isRunning){
                val dx:Float= if (Random().nextBoolean()) 100f else -100f
                objectTranslation.setFloatValues(imgView!!.x,imgView!!.x+dx)
                viewModel.dx = dx
                objectTranslation.start()
            }
        }
    }

}