package com.liyaan.calculationtest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.databinding.FragmentQuestionBinding
import com.liyaan.calculationtest.databinding.FragmentWinBinding
import com.liyaan.calculationtest.viewModel.MyViewModel


class WinFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mViewModel: MyViewModel = ViewModelProviders.of(requireActivity(),
                SavedStateViewModelFactory(requireActivity().application,this)).get(MyViewModel::class.java)
        val binding: FragmentWinBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_win,container,false)
        binding.data = mViewModel
        binding.lifecycleOwner = requireActivity()
        binding.button14.setOnClickListener {
            val controlle = Navigation.findNavController(it)
            controlle.navigate(R.id.action_winFragment_to_tiltleFragment)
        }
        return binding.root
    }

}