package com.glee.gbox

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

/**
 *  @author liji
 *  @date  2018/11/15 13:55
 *  description
 */


abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel> : Fragment() {
    lateinit var viewModel: VM
    lateinit var binding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, viewBinder.layoutResId, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("glee9507",this::class.java.name)
        viewModel = createViewModel()
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.setVariable(viewBinder.viewModelId,viewModel)
        observerLiveData(viewLifecycleOwner)
    }

    abstract val viewBinder: ViewBinder

    abstract fun createViewModel(): VM

    fun observerLiveData(viewLifecycleOwner: LifecycleOwner) {}
}