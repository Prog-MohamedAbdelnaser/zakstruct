package com.zaka.features.main.letters

import android.view.View
import com.base.BaseFragment
import com.zaka.databinding.FragmentLettersBinding

class LettersFragment : BaseFragment() {

    private var _binding: FragmentLettersBinding? = null
    override fun inflateBinding(inflateId:View) {
        _binding = FragmentLettersBinding.bind(inflateId)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}