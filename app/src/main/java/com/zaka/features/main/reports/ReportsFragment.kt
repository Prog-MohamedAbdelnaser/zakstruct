package com.zaka.features.main.reports

import android.view.View
import com.base.BaseFragment
import com.zaka.databinding.FragmentReportsBinding

class ReportsFragment : BaseFragment() {

    private var _binding: FragmentReportsBinding? = null
    override fun inflateBinding(inflateId: View) {
        _binding=FragmentReportsBinding.bind(inflateId)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}