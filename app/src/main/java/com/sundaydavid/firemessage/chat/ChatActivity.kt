package com.sundaydavid.firemessage.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.sundaydavid.firemessage.AppConstants
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.model.MessageType
import com.sundaydavid.firemessage.model.TextMessage
import com.sundaydavid.firemessage.util.FireStoreUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.toast
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var shouldInitRecyclerView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(AppConstants.USER_NAME)

        val otherUserId = intent.getStringExtra(AppConstants.USER_ID)
        FireStoreUtil.getOrCreateChatChannel(otherUserId!!) {channelId ->  
            messagesListenerRegistration =
                FireStoreUtil.addChatMessagesListener(channelId, this, this::updateRecyclerView)

            imageView_send.setOnClickListener {
                val messageToSend =
                    TextMessage(editTest_message.text.toString(), Calendar.getInstance().time,
                                FirebaseAuth.getInstance().currentUser!!.uid, MessageType.TEXT)
                editTest_message.setText("")
                FireStoreUtil.sendMessage(messageToSend, channelId)
            }
            fab_send_image.setOnClickListener {
                //TODO: Send Image Messages
            }
        }
    }
    private fun updateRecyclerView(messages: List<Item>) {
        fun init(){

        }
        fun updateItems(){

        }
    }
}