package com.imobile3.groovypayments.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.imobile3.groovypayments.R

class MainFragment : Fragment(R.layout.main_fragment) {
    private val mViewModel: MainViewModel by viewModels()

    companion object {
        private val TAG = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }
}