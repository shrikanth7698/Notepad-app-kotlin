package com.shrikanthravi.kotpad.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shrikanthravi.kotpad.NewNoteActivity
import com.shrikanthravi.kotpad.R
import com.shrikanthravi.kotpad.data.DataStore
import com.shrikanthravi.kotpad.data.Note
import com.shrikanthravi.kotpad.util.layoutInflater
import kotlinx.android.synthetic.main.note_row_item.view.*
import org.jetbrains.anko.startActivity


import java.util.ArrayList

class NotesAdapter(private val context: Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var notes: List<Note> = ArrayList()
    private var isRefreshing = false

    init {
        setHasStableIds(true)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        refresh()
    }

    override fun getItemId(position: Int): Long {
        return notes[position].id.toLong()
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
            val view = context.layoutInflater.inflate(R.layout.note_row_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.text.text = note.text
        holder.title.text = note.title
        holder.noteCV.setOnClickListener{
            context.startActivity<NewNoteActivity>(
                "note" to note
            )
        }

    }

    fun refresh() {
        if (isRefreshing) return
        isRefreshing = true
        DataStore.execute {
            val notes = DataStore.notes.getAll()
            Handler(Looper.getMainLooper()).post {
                this@NotesAdapter.notes = notes
                notifyDataSetChanged()
                isRefreshing = false
            }
        }
    }

    class NotesViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val noteCV = itemView.noteCV
        val text = itemView.textTV
        val title = itemView.titleTV


    }
}
