package com.dact.notetify

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.dact.notetify.adapter.NoteAdapter
import com.dact.notetify.database.NoteDatabase
import com.dact.notetify.database.table.Note
import com.dact.notetify.screen.AddNoteActivity
import com.dact.notetify.screen.OnboardActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEdit: SharedPreferences.Editor
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var noteAdapter: NoteAdapter
    private var noteArrayList = arrayListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVar()

        getData()

        addButton.setOnClickListener{
            intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)

        }

        noteAdapter = NoteAdapter(noteArrayList, this)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter
        recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        noteArrayList.clear()

        getData()

        noteAdapter.notifyDataSetChanged()
        Log.d("test resume", "ter resume ")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        if (isFirstTime()){
            intent = Intent(this, OnboardActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initVar(){
        sharedPref = getSharedPreferences("prefs", MODE_PRIVATE)
        sharedPrefEdit = sharedPref.edit()
        addButton = findViewById(R.id.floatingActionButton)

    }

    private fun isFirstTime(): Boolean {
        return if(sharedPref.getBoolean("firstTime", true)){
            sharedPrefEdit.putBoolean("firstTime", false)
            sharedPrefEdit.apply()
            true
        }else{
            false
        }
    }

    private fun getData(){
        val db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "db-note"
        ).allowMainThreadQueries().build()

        val noteDao = db.noteDao()

        val getNote = noteDao.getAllNotes()
        for(x in getNote){
            Log.d("LoopgetNote", "$x")
            noteArrayList.add(x)
        }
        for(x in noteArrayList){
            Log.d("loopArrayList2", "$x")
        }
    }

}