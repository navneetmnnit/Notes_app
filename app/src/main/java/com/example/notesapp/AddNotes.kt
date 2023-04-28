package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.Models.Note
import com.example.notesapp.databinding.ActivityAddNotesBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNotes : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding

    private lateinit var note: Note
    private lateinit var oldNote: Note
    private var isUpdate = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try{
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true

        }catch (e: Exception){
            e.printStackTrace()
        }
        binding.icCheck.setOnClickListener{

            val tile = binding.etTitle.text.toString()
            val noteDesc = binding.etNote.text.toString()

            if(tile.isNotEmpty() || noteDesc.isNotEmpty()){

                val formatter = SimpleDateFormat("EEE ,d MM yyyy HM:mm a")

                note = if(isUpdate){
                    Note(oldNote.id, tile,noteDesc,formatter.format(Date()))
                } else{
                    Note(null, tile,noteDesc,formatter.format(Date()))
                }

                val intent = Intent()
                intent.putExtra("note",note)
                setResult(RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@AddNotes, "Please Enter Some Data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.icBackArrow.setOnClickListener{
            onBackPressed()
        }
    }
}