package com.zaka.features.main.chatrooms

import android.view.View
import android.view.ViewStub
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BaseFragment
import com.base.adapter.BaseAdapter
import com.zaka.R
import com.zaka.databinding.FragmentChatRoomsBinding

class ChatRoomsFragment : BaseFragment() {

    private  var chatList:  ArrayList<String>? = null
    private var adapter: ChatRoomsAdapter? = null
    private var _binding: FragmentChatRoomsBinding? = null
    override fun layoutResource(): Int = R.layout.fragment_chat_rooms
    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding= FragmentChatRoomsBinding.bind(inflateView)

        chatList = arrayListOf()
        chatList!!.add("asdsad")
        chatList!!.add("asdsad")
        chatList!!.add("asdsad")
        chatList!!.add("asdsad")
        chatList!!.add("asdsad")

        adapter=ChatRoomsAdapter(chatList,R.layout.messages_item)

    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}