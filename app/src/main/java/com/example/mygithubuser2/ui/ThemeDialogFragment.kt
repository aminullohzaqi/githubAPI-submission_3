package com.example.mygithubuser2.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mygithubuser2.R
import com.example.mygithubuser2.databinding.FragmentThemeDialogBinding

class ThemeDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentThemeDialogBinding
    private var optionDialogListener: OnThemeDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChooseTheme.setOnClickListener{
            val checkedRadioButton = binding.rgTheme.checkedRadioButtonId
            if(checkedRadioButton != -1){
                var theme: String? = null
                when(checkedRadioButton){
                    R.id.rd_light -> theme = "light"

                    R.id.rd_dark -> theme = "dark"
                }
                optionDialogListener?.onThemeChosen(theme)
                dialog?.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnThemeDialogListener) {
            optionDialogListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    interface OnThemeDialogListener {
        fun onThemeChosen(text: String?)
    }
}