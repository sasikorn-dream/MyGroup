package com.cis.mygroupapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit  var auth: FirebaseAuth
    lateinit var googleClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()//เก็บเอาค่า LOgin
        button1.setOnClickListener({v->signIn()})//บังคับให้login
        //button2.setOnClickListener({v->signOut()})

        var gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this,gso)
        auth = FirebaseAuth.getInstance()
    }

    private fun signOut(){
        auth.signOut()
        googleClient.signOut().addOnCompleteListener(this){
            updateUI(null)
        }
    }
    public fun updateUI(user: FirebaseUser?){
       if(user == null){
            //textView1.text = "Signed Out"
        }else{
            //textView1.text = "Sign In as "+ user.email
            val i = Intent(this,add_activity::class.java)
           //i.putExtra("username",textView1.getText().toString())
                i.putExtra("name",user.email)
            //Toast.makeText(this,"Log in with "+user.email,Toast.LENGTH_LONG).show()
            startActivity(i)
        }
    }

    private  fun signIn(){
        var signInIntent = googleClient.signInIntent
        startActivityForResult(signInIntent,101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                firebaseAuth(account!!)
            }catch (e:ApiException){
                //Log
                //display UI
                updateUI(null)
            }
        }
    }

    private fun firebaseAuth(account: GoogleSignInAccount) {
        val credential =
            GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    updateUI(null)
                }
            }
    }
}
