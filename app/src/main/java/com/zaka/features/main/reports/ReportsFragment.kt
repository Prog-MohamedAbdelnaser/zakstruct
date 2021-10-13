package com.zaka.features.main.reports

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentLettersBinding
import com.zaka.databinding.FragmentReportsBinding

class ReportsFragment : BaseFragment() {

    private var _binding: FragmentReportsBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_reports

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentReportsBinding.bind(inflateView)
        _binding!!.textHome!!.text="hi mohamed"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}