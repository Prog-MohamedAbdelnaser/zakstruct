package com.zaka.features.main.chatrooms

import android.util.Log
import android.view.View
import com.base.adapter.BaseViewHolder

class ChatRoomsViewHolder(itemView: View) :BaseViewHolder<String>(itemView) {


    override fun onClick(v: View?) {
        super.onClick(v)
        v?.setOnClickListener {
            Log.e("gg","gg")
        }
    }

    override fun fillData() {

    }
}