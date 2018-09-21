package droiddevelopers254.devfestnairobi.views.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import droiddevelopers254.devfestnairobi.HomeActivity
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.viewmodels.AuthenticateUserViewModel
import kotlinx.android.synthetic.main.content_authenticate_user.*
import org.jetbrains.anko.toast

class AuthenticateUserActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var pDialog: SweetAlertDialog? = null
    private lateinit var authenticateUserViewModel: AuthenticateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout tu fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = ContextCompat.getColor(this, R.color.mdtp_transparent_black)
        }

        setContentView(R.layout.activity_authenticate_user)

        authenticateUserViewModel = ViewModelProviders.of(this).get(AuthenticateUserViewModel::class.java)

        // Progress dialog
        pDialog = SweetAlertDialog(this@AuthenticateUserActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.progressHelper?.barColor = Color.parseColor("#863B96")
        pDialog?.setCancelable(false)

        //check whether the user is signed in first
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            navigateToHome()
        } else {
            // not signed in
            showUI()
        }
    }
    private fun showUI() {
        googleSignInBtn.setOnClickListener {
            pDialog?.titleText = "Signing in"
           // showDialog()
            signInUser()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
               navigateToHome()
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    toast("You pressed back button before log in")
                    return
                }
                if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    toast("Network Error")
                    return
                }
                if (response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    toast("Please try again")
                }
                toast("An error occurred")
            }

        }
    }
    //function to log in
    private fun signInUser() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                        .build(),
                RC_SIGN_IN)
    }
    private fun navigateToHome() {
        val intent = Intent(this@AuthenticateUserActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }
    private fun showDialog() {
        if (!pDialog!!.isShowing)
            pDialog?.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing)
            pDialog?.dismiss()

    }
    companion object {
        private const val RC_SIGN_IN = 123
    }

}
