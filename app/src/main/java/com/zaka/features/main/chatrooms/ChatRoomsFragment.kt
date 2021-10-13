package com.zaka.features.main.chatrooms

import android.view.View
import android.view.ViewStub
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentChatRoomsBinding

class ChatRoomsFragment : BaseFragment() {

    private var _binding: FragmentChatRoomsBinding? = null
    override fun layoutResource(): Int = R.layout.fragment_chat_rooms
    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding= FragmentChatRoomsBinding.bind(inflateView)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}