package com.cis.mygroupapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_activity.*
import kotlinx.android.synthetic.main.activity_main.*

class add_activity : AppCompatActivity() {

    lateinit  var auth: FirebaseAuth
    lateinit var googleClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activity)

        val name = getIntent().getStringExtra("name")
        textView.text = "                      Sign in with \n $name"



        auth = FirebaseAuth.getInstance()//เก็บเอาค่า LOgin
        //var signoutbtn = findViewById(R.id.SignOutbtn) as Button
        SignOutbtn.setOnClickListener({v->signOut() })

        var gso = GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this,gso)
        auth = FirebaseAuth.getInstance()

        val arrayAdapter: ArrayAdapter<*>
        val students_array = resources.getStringArray(R.array.students_array)

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students_array)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as String
            val intent = Intent(this, eventlistActivity::class.java)


            //i.putExtra("username",textView1.getText().toString())
            intent.putExtra("name",selectedItem)
            //Toast.makeText(this,"Log in with "+user.email,Toast.LENGTH_LONG).show()

            startActivity(intent)
            //val intent = Intent(this, BookDetailActivity::class.java)
            //startActivity(intent)
        }

    }

    private fun signOut(){
        auth.signOut()
        googleClient.signOut().addOnCompleteListener(this){
            updateUI(null)
        }
    }
    public fun updateUI(user: FirebaseUser?) {
        val i = Intent(this, MainActivity::class.java)
        Toast.makeText(this, "Sign Out Success" , Toast.LENGTH_SHORT).show()
        startActivity(i)

    }




}
