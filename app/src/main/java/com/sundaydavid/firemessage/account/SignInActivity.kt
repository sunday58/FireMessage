package com.sundaydavid.firemessage.account

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sundaydavid.firemessage.MainActivity
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.service.MyFirebaseInstanceIDService
import com.sundaydavid.firemessage.util.FireStoreUtil
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SignInActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1

    private val signInProviders =
        listOf(AuthUI.IdpConfig.EmailBuilder()
            .setAllowNewAccounts(true)
            .setRequireName(true)
            .build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        account_sign_in.setOnClickListener{
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .setLogo(R.drawable.ic_favorite_heart_button)
                .build()
            startActivityForResult(intent,RC_SIGN_IN )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("setting up your account")
                FireStoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<MainActivity>().newTask().clearTask())

                   FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {instanceIdResult ->
                       val registrationToken = instanceIdResult.token

                       MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)
                   }
                    progressDialog.dismiss()
                }

            }
            else if(resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when(response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK ->
                      constraint_layout.longSnackbar("No network")
                    ErrorCodes.UNKNOWN_ERROR ->
                        constraint_layout.longSnackbar("unknown error")

                }
            }
        }
    }
}