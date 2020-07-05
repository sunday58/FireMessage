package com.sundaydavid.firemessage.recyclerView.item

import android.content.Context
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.glide.GlideApp
import com.sundaydavid.firemessage.model.ImageMessage
import com.sundaydavid.firemessage.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.item_image_message.*

class ImageMessageItem(val message: ImageMessage,
                        val context: Context)
    : MessageItem(message){


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        super.bind(viewHolder, position)
        GlideApp.with(context)
            .load(StorageUtil.pathToReference(message.imagePath))
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(viewHolder.imageView_message_image)
    }
    override fun getLayout() = R.layout.item_image_message

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean {
        if (other !is ImageMessageItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as ImageMessageItem)
    }

    override fun hashCode(): Int {
        return message.hashCode()
    }
}