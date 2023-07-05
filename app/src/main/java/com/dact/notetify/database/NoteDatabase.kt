package com.dact.notetify.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dact.notetify.database.table.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}