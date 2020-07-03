package com.sundaydavid.firemessage.recyclerView.item

import android.content.Context
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.glide.GlideApp
import com.sundaydavid.firemessage.model.User
import com.sundaydavid.firemessage.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_person.*

class PersonItem (val person: User,
                    val userId: String,
                    private val context: Context)
    : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textView_name.text = person.name
        viewHolder.textView_bio.text = person.bio
        if (person.profilePicturePath != null)
            GlideApp.with(context)
                .load(StorageUtil.pathToReference(person.profilePicturePath))
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(viewHolder.imageView_profile_picture)
    }

    override fun getLayout() = R.layout.item_person

}