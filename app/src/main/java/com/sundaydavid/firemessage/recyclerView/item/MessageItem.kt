package com.sundaydavid.firemessage.recyclerView.item

import android.view.Gravity
import android.widget.FrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.model.Message
import com.sundaydavid.firemessage.model.MessageType
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_image_message.view.*
import kotlinx.android.synthetic.main.item_text_message.*
import kotlinx.android.synthetic.main.item_text_message.view.*
import kotlinx.android.synthetic.main.item_text_message.view.textView_message_time
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.wrapContent
import java.text.SimpleDateFormat

abstract class MessageItem(private val message: Message)
    : Item(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        setTimeText(viewHolder)
        setMessageRootGravity(viewHolder)
    }


    private fun setTimeText(viewHolder: GroupieViewHolder){
        val dateFormat = SimpleDateFormat
            .getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        viewHolder.textView_message_time.text = dateFormat.format(message.time)
    }

    private fun setMessageRootGravity(viewHolder: GroupieViewHolder){
        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid){
            viewHolder.message_root.apply {
                backgroundResource = R.drawable.rect_round_white
                if (viewHolder.textView_message_text != null) {
                    textView_message_text.setTextColor(resources.getColor(R.color.black))
                    textView_message_time.setTextColor(resources.getColor(R.color.black))
                    val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.END)
                    this.layoutParams = lParams
                }else {
                    textView_message_time.setTextColor(resources.getColor(R.color.black))
                    val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.END)
                    this.layoutParams = lParams
                }
            }
        }
        else {
            viewHolder.message_root.apply {
                backgroundResource = R.drawable.rect_round_primary_color
                if (viewHolder.textView_message_text != null) {
                    textView_message_text.setTextColor(resources.getColor(R.color.white))
                    textView_message_time.setTextColor(resources.getColor(R.color.white))
                    val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.START)
                    this.layoutParams = lParams
                }else {
                    textView_message_time.setTextColor(resources.getColor(R.color.white))
                    val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.START)
                    this.layoutParams = lParams
                }
            }
        }
    }
}