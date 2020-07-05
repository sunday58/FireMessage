package com.sundaydavid.firemessage.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.sundaydavid.firemessage.util.FireStoreUtil


class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        
        super.onNewToken(p0)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val newRegistrationToken = instanceIdResult.token
            if (FirebaseAuth.getInstance().currentUser != null)
                addTokenToFirestore(newRegistrationToken)
        }

    }


    
    companion object {
        fun addTokenToFirestore(newRegistrationToken: String?) {
            if (newRegistrationToken == null) throw  NullPointerException("FCM token is null")

            FireStoreUtil.getFCMRegistrationToken {tokens ->
                if (tokens.contains(newRegistrationToken))
                    return@getFCMRegistrationToken

                    tokens.add(newRegistrationToken)
                    FireStoreUtil.setFCMRegistrationToken(tokens)
            }
        }
    }
    }
