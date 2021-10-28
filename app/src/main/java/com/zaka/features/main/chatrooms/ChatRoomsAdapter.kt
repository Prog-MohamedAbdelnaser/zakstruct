package com.zaka.features.main.chatrooms

import android.view.LayoutInflater
import android.view.ViewGroup
import com.base.adapter.BaseAdapter
import com.base.adapter.BaseViewHolder
import com.zaka.R

class ChatRoomsAdapter(items: ArrayList<String>? = null, itemLayoutRes: Int? = null) :
    BaseAdapter<String>(items, itemLayoutRes) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {

        return if (viewType==1) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.messages_item, parent, false)
            ChatRoomsViewHolder(view)
        }else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.messages_item, parent, false)
            ChatRoomsViewHolder(view)
        }

    }


}