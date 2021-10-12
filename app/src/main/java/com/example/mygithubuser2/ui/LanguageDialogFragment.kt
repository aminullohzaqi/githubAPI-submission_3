package com.example.mygithubuser2.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mygithubuser2.R
import com.example.mygithubuser2.databinding.FragmentLanguageDialogBinding


class LanguageDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentLanguageDialogBinding
    private var optionDialogListener: OnOptionDialogListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChooseLanguage.setOnClickListener{
            val checkedRadioButton = binding.rgLanguage.checkedRadioButtonId
            if(checkedRadioButton != -1){
                var language: String? = null
                when(checkedRadioButton){
                    R.id.rd_english -> language = "en"

                    R.id.rd_indonesia -> language = "in"
                }
                optionDialogListener?.onOptionChosen(language)
                dialog?.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnOptionDialogListener) {
            optionDialogListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }
}