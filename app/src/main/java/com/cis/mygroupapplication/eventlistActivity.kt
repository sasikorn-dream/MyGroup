package com.cis.mygroupapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_eventlist.*
import kotlinx.android.synthetic.main.activity_save_activity.*

class eventlistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventlist)


        val arrayAdapter: ArrayAdapter<*>
        val name = getIntent().getStringExtra("name")
        textView6.text = name
        val students_array = resources.getStringArray(R.array.events_array)

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students_array)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as String
//            val intent = Intent(this, detaileventActivity::class.java)
//            startActivity(intent)
            //val intent = Intent(this, BookDetailActivity::class.java)
            //startActivity(intent)
        }
    }
}
