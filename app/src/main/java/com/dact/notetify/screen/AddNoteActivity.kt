package com.dact.notetify.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.room.Room
import com.dact.notetify.MainActivity
import com.dact.notetify.R
import com.dact.notetify.database.NoteDatabase
import com.dact.notetify.database.table.Note

class AddNoteActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var title: EditText
    private lateinit var content: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        backButton = findViewById(R.id.backButton)
        title = findViewById(R.id.addNoteTitle)
        content = findViewById(R.id.addNoteText)

        val db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "db-note"
        ).allowMainThreadQueries().build()
        val noteDao = db.noteDao()

        val currentId = intent.getIntExtra("id", 0)

        if (currentId != 0){
            val currentNote = noteDao.getNoteById(currentId.toInt())
            val currentTitle = currentNote.title
            title.setText(currentNote.title)
            content.setText(currentNote.content)

        }

        backButton.setOnClickListener {
            val judul = title.text.toString()
            val isi = content.text.toString()
            val waktu = System.currentTimeMillis()
            var note = Note(title = judul, content = isi, timestamp = waktu)
            if(judul.isNotBlank() && isi.isNotBlank()){
                if(currentId != 0){
                    note = Note(id = currentId, title = judul, content = isi, timestamp = waktu)
                    noteDao.update(note)
                }else{
                    noteDao.insert(note)
                }
            }

            finish()
        }


    }
}