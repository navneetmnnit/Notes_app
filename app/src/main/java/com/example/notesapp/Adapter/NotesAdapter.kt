package com.example.notesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Models.Note
import com.example.notesapp.R
import kotlin.random.Random


class NotesAdapter(private val context: Context, private val listener: NotesClickedListener): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    private val notesList = ArrayList<Note>()
    private val fullNotesList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNotes = notesList[position]
        holder.title.text = currentNotes.title
        holder.title.isSelected = true

        holder.note_tv.text = currentNotes.note
        holder.date.text = currentNotes.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))

        holder.notes_layout.setOnClickListener {
            listener.onNoteClicked(notesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onNoteLongClicked(notesList[holder.adapterPosition],holder.notes_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateList(newList: List<Note>){

        fullNotesList.clear()
        fullNotesList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullNotesList)
        notifyDataSetChanged()

    }

    fun filterList(search: String){
        notesList.clear()

        for(item in fullNotesList){
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                    item.note?.lowercase()?.contains(search.lowercase()) == true) {

                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    private fun randomColor(): Int{

        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)


        val seed =  System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]

    }

    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val notes_layout = itemView.findViewById<CardView>(R.id.card_view_layout)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val note_tv: TextView = itemView.findViewById(R.id.tv_notes)
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }

    interface NotesClickedListener{
        fun onNoteClicked(note: Note)
        fun onNoteLongClicked(note: Note,cardView: CardView)
    }
}