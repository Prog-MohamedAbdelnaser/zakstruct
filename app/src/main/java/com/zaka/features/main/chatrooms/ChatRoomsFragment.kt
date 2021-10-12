package com.zaka.features.main.chatrooms

import android.view.View
import com.base.BaseFragment
import com.zaka.databinding.FragmentChatRoomsBinding

class ChatRoomsFragment : BaseFragment() {

    private var _binding: FragmentChatRoomsBinding? = null

    override fun inflateBinding( inflateId: View) {
        _binding = FragmentChatRoomsBinding.bind(inflateId)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}