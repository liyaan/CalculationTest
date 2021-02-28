package com.liyaan.calculationtest.buttomFragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.liyaan.calculationtest.R

class SecondFragment : Fragment() {
    private var imgView: ImageView? = null
    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.second_fragment, container, false)
        imgView = view.findViewById(R.id.imageView)
        return view
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SecondViewModel::class.java)
        imgView!!.scaleX = 1+viewModel.scalePoint
        imgView!!.scaleY= 1+viewModel.scalePoint
        val objectAnimationX = ObjectAnimator.ofFloat(imgView,"scaleX",0f,0f)
        val objectAnimationY = ObjectAnimator.ofFloat(imgView,"scaleY",0f,0f)
        objectAnimationX.duration = 500
        objectAnimationY.duration = 500
        imgView!!.setOnClickListener {
            if (!objectAnimationX.isRunning && !objectAnimationY.isRunning){
                objectAnimationX.setFloatValues(imgView!!.scaleX+0.1f)
                objectAnimationY.setFloatValues(imgView!!.scaleY+0.1f)
                viewModel.scalePoint +=0.1f
                objectAnimationX.start()
                objectAnimationY.start()
            }
        }
    }

}