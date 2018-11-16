package com.glee.gbox.main

import androidx.lifecycle.ViewModelProviders
import com.glee.gbox.BR
import com.glee.gbox.BaseFragment
import com.glee.gbox.R
import com.glee.gbox.ViewBinder
import com.glee.gbox.databinding.FragmentMainBinding

/**
 *  @author liji
 *  @date  2018/11/15 13:54
 *  description
 */


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    companion object {
        val TAG = MainFragment::class.java.name
        fun newInstance() = MainFragment()
    }

    override val viewBinder = ViewBinder(R.layout.fragment_main, BR.viewModel)

    override fun createViewModel() = ViewModelProviders.of(this)[MainViewModel::class.java]

}