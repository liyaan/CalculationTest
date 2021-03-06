package com.liyaan.viewpagetwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.liyaan.viewpagetwo.fragment.RotateFragment
import com.liyaan.viewpagetwo.fragment.ScaleFragment
import com.liyaan.viewpagetwo.fragment.TranslateFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount()=3

            override fun createFragment(position: Int)=
                    when(position){
                        0->ScaleFragment()
                        1->RotateFragment()
                        else->TranslateFragment()
                    }
        }

        TabLayoutMediator(tabLayout,viewPager2){tab, position ->
            when(position){
                0->tab.text="缩放"
                1->tab.text="旋转"
                else-> tab.text = "移动"
            }
        }.attach()
    }
}