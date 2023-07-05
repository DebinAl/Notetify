package com.dact.notetify.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.dact.notetify.R
import com.dact.notetify.database.NoteDatabase
import com.dact.notetify.database.table.Note
import com.dact.notetify.screen.AddNoteActivity

class NoteAdapter(private val notes: ArrayList<Note>, private val context: Context): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDateTime.text = currentNote.content

        holder.delete.setOnClickListener{
            getData(currentNote)
            notes.removeAt(position)
            notifyDataSetChanged()
        }
        holder.itemClick.setOnClickListener {
            val intent = Intent(context, AddNoteActivity::class.java).putExtra("id", currentNote.id)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById(R.id.judulItem)
        val textViewDateTime: TextView = itemView.findViewById(R.id.datetimeItem)
        val itemClick: RelativeLayout = itemView.findViewById(R.id.itemNote)
        val delete: ImageView = itemView.findViewById(R.id.optionDelete)
    }

    private fun getData(curNote: Note){
        val db = Room.databaseBuilder(
            context,
            NoteDatabase::class.java, "db-note"
        ).allowMainThreadQueries().build()

        val noteDao = db.noteDao()

        noteDao.delete(curNote)
    }
}