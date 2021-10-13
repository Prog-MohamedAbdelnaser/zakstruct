package com.zaka.features.main.letters

import android.view.View
import android.view.ViewStub
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentChatRoomsBinding
import com.zaka.databinding.FragmentLettersBinding

class LettersFragment : BaseFragment() {

    private var _binding: FragmentLettersBinding? = null

    override fun layoutResource(): Int = R.layout.fragment_letters

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding= FragmentLettersBinding.bind(inflateView)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}