package com.liyaan.calculationtest.fragment

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
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
import com.liyaan.calculationtest.databinding.FragmentTiltleBinding
import com.liyaan.calculationtest.viewModel.MyViewModel
import java.lang.StringBuilder

class QuestionFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mViewModel: MyViewModel = ViewModelProviders.of(requireActivity(),
                SavedStateViewModelFactory(requireActivity().application,this)).get(MyViewModel::class.java)
        mViewModel.generator()
        mViewModel.getCurrentScore().value = 0
        val binding: FragmentQuestionBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_question,container,false)
        binding.data = mViewModel
        binding.lifecycleOwner = requireActivity()
        val stringBuilder = StringBuilder()
        val listener =  View.OnClickListener {
            when(it.id){
                R.id.button1->stringBuilder.append("1")
                R.id.button2->stringBuilder.append("2")
                R.id.button3->stringBuilder.append("3")
                R.id.button4->stringBuilder.append("4")
                R.id.button5->stringBuilder.append("5")
                R.id.button6->stringBuilder.append("6")
                R.id.button7->stringBuilder.append("7")
                R.id.button8->stringBuilder.append("8")
                R.id.button9->stringBuilder.append("9")
                R.id.buttonClear->stringBuilder.setLength(0)
                R.id.button11->stringBuilder.append("0")
            }
            if (stringBuilder.isEmpty()){
                binding.textView9.text = getString(R.string.input_indicator)
            }else{
                binding.textView9.text = stringBuilder.toString()
            }
        }
        binding.button1.setOnClickListener(listener)
        binding.button2.setOnClickListener(listener)
        binding.button3.setOnClickListener(listener)
        binding.button4.setOnClickListener(listener)
        binding.button5.setOnClickListener(listener)
        binding.button6.setOnClickListener(listener)
        binding.button7.setOnClickListener(listener)
        binding.button8.setOnClickListener(listener)
        binding.button9.setOnClickListener(listener)
        binding.buttonClear.setOnClickListener(listener)
        binding.button11.setOnClickListener(listener)

        binding.button12.setOnClickListener {
            if(stringBuilder.isEmpty()){
                stringBuilder.append("-1")
            }
            if (stringBuilder.toString().toInt() == mViewModel.getAnswer().value){
                mViewModel.answerCount()
                stringBuilder.setLength(0)
                binding.textView9.text = getString(R.string.answer_correct_message)
            }else{
                val controlle = Navigation.findNavController(it)
                if (mViewModel.win_flag){
                    controlle.navigate(R.id.action_questionFragment_to_winFragment)
                    mViewModel.win_flag = false
                    mViewModel.save()
                }else{
                    controlle.navigate(R.id.action_questionFragment_to_loseFragment)
                }
                mViewModel.GO_TO_TIME = 0
            }
        }
        if (mViewModel.GO_TO_TIME==0L){
            mViewModel.GO_TO_TIME = SystemClock.elapsedRealtime()
            lifecycle.addObserver(binding.textView14)
        }else{
            binding.textView14.base = mViewModel.GO_TO_TIME
        }
        binding.textView14.start();
//        binding.button.setOnClickListener {
//            val controlle = Navigation.findNavController(it)
//            controlle.navigate(R.id.action_tiltleFragment_to_questionFragment)
//        }
        return binding.root
    }
}