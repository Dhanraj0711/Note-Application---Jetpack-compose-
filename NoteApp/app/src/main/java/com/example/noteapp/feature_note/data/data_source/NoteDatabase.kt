package com.example.noteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.noteapp.feature_note.domain.model.Note

@Database(
    entities = [
        Note::class
    ],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}