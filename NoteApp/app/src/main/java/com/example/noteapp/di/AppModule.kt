package com.example.noteapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDao
import com.example.noteapp.feature_note.data.data_source.NoteDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImp
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext app: Context): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        "dbName"
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: NoteDatabase): NoteDao =
        database.noteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImp(database.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUserCase(repository: NoteRepository): NoteUserCase {
        return NoteUserCase(
            getNotes = GetNotes(repository = repository),
            deleteNote = DeleteNote(repository = repository),
            addNote = AddNote(repository = repository),
            getNote = GetNote(repository = repository)
        )
    }
}