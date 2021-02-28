package com.liyaan.calculationtest.buttomFragment

import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.liyaan.calculationtest.R

class FristFragment : Fragment() {
    private var imgView:ImageView? = null
    companion object {
        fun newInstance() = FristFragment()
    }

    private lateinit var viewModel: FristViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frist_fragment, container, false)
        imgView = view.findViewById(R.id.imageView4)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FristViewModel::class.java)
        val objectAnim = ObjectAnimator.ofFloat(imgView!!,"rotation",0f,0f)
        imgView!!.rotation = viewModel.rotateAnimation
        objectAnim.duration = 500
        imgView!!.setOnClickListener {
            if (!objectAnim.isRunning){
                objectAnim.setFloatValues(imgView!!.rotation,imgView!!.rotation+180)
                viewModel.rotateAnimation+=180
                objectAnim.start()
            }
        }
    }

}