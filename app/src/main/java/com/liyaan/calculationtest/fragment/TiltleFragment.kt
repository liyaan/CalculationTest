package com.liyaan.calculationtest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.databinding.FragmentTiltleBinding
import com.liyaan.calculationtest.viewModel.MyViewModel


class TiltleFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mViewModel:MyViewModel = ViewModelProviders.of(requireActivity(),
                SavedStateViewModelFactory(requireActivity().application,this)).get(MyViewModel::class.java)
        val binding:FragmentTiltleBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_tiltle,container,false)
        binding.data = mViewModel
        binding.lifecycleOwner = requireActivity()
        binding.button.setOnClickListener {
            val controlle = Navigation.findNavController(it)
            controlle.navigate(R.id.action_tiltleFragment_to_questionFragment)
        }
        return binding.root
    }


}