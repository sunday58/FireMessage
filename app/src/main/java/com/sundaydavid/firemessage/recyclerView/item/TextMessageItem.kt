package com.sundaydavid.firemessage.recyclerView.item

import android.content.Context
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.model.TextMessage
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class TextMessageItem(val message: TextMessage,
                        context: Context)
    : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //TODO: placeholder bind
    }

    override fun getLayout()= R.layout.item_text_message
}