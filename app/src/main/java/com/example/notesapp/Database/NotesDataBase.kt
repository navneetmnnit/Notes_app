package com.example.notesapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.Models.Note
import com.example.notesapp.Utility.DATABASE_NAME

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NotesDataBase: RoomDatabase() {

    abstract fun getNoteData(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NotesDataBase? = null

        fun getDatabase(context: Context) : NotesDataBase{
            return INSTANCE?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDataBase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}