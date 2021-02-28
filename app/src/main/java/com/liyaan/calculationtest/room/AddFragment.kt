package com.liyaan.calculationtest.room

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.liyaan.calculationtest.R



class AddFragment : Fragment() {
    private var editTextEnglish: EditText? = null
    private var editTextChina: EditText? = null
    private var buttonSubmit: Button? = null
    var mRoomViewModel:RoomViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRoomViewModel = ViewModelProviders.of(requireActivity()).get(RoomViewModel::class.java)
        editTextEnglish = requireActivity().findViewById(R.id.editTextEnglish)
        editTextChina = requireActivity().findViewById(R.id.editTextChina)
        buttonSubmit = requireActivity().findViewById(R.id.buttonSubmit)
        buttonSubmit!!.isClickable = false
        editTextEnglish!!.requestFocus()
        val imm:InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextEnglish,0)
        val textWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val strEnglish = editTextEnglish!!.text.toString().trim()
                val strChina = editTextChina!!.text.toString().trim()
                buttonSubmit!!.isEnabled = strEnglish.isNotEmpty() && strChina.isNotEmpty()
            }

        }
        editTextEnglish!!.addTextChangedListener(textWatcher)
        editTextChina!!.addTextChangedListener(textWatcher)
        buttonSubmit!!.setOnClickListener {
            val english = editTextEnglish!!.text.toString().trim()
            val china = editTextChina!!.text.toString().trim()
            val word = WordEntity(english,china)
            mRoomViewModel!!.insertWord(word)
            val nav: NavController = Navigation.findNavController(it)
            nav.navigateUp()
            val imm:InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
    }
}