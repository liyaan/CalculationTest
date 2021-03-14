package com.liyaan.viewpagetwo.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liyaan.viewpagetwo.R
import kotlinx.android.synthetic.main.fragment_rotate.*


class RotateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rotate, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewRotate.setOnClickListener {
            ObjectAnimator.ofFloat(it,"rotation",it.rotation+30f).start()
        }
    }
}