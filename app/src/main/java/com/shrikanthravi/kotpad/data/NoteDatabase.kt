package com.shrikanthravi.kotpad.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.CREATED_AT
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.IS_PINNED
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.TEXT
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.TITLE
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.UPDATED_AT
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable._ID
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable._TABLE_NAME
import org.jetbrains.anko.db.transaction
import java.util.*

class NoteDatabase(context: Context) {

    private val helper: NotesOpenHelper = NotesOpenHelper(context)

    fun getAll(): List<Note> {
        val cursor = helper.readableDatabase.query(_TABLE_NAME, null, null, null, null, null,
            CREATED_AT)
        return cursor.use(this::allFromCursor)

    }

    fun loadAllByIds(vararg ids: Int): List<Note> {
        val questionMarks = ids.map { "?" }.joinToString {", "}
        val args = ids.map { it.toString() }
        val selection = "${BaseColumns._ID} IN ($questionMarks)"
        val cursor = helper.readableDatabase.query(_TABLE_NAME, null,
            selection,
            args.toTypedArray(),
            null,
            null,
            CREATED_AT)
        return cursor.use(this::allFromCursor)
    }

    fun insert(vararg notes: Note) {
        val values = fromNotes(notes)
        val db = helper.writableDatabase
        db.transaction {
            for (value in values) {
                db.insert(_TABLE_NAME, null, value)
            }
        }

    }

    fun update(note: Note) {
        val values = fromNote(note)
        helper.writableDatabase.update(_TABLE_NAME,
            values,
            "${_ID} = ?",
            arrayOf(Integer.toString(note.id)))
    }

    fun delete(note: Note) {
        helper.writableDatabase.delete(_TABLE_NAME,
            "${_ID} = ?",
            arrayOf(Integer.toString(note.id)))
    }

    private fun fromCursor(cursor: Cursor): Note {
        var col = 0
        val note = Note().apply {
            id = cursor.getInt(col++)
            title = cursor.getString(col++)
            text = cursor.getString(col++)
            isPinned = cursor.getInt(col++) != 0
            createdAt = Date(cursor.getLong(col++))
            updatedAt = Date(cursor.getLong(col))
        }

        return note
    }

    private fun allFromCursor(cursor: Cursor): List<Note> {
        val retval = ArrayList<Note>()
        while (cursor.moveToNext()) {
            retval.add(fromCursor(cursor))
        }
        return retval
    }

    private fun fromNote(note: Note): ContentValues {
        return ContentValues().apply {
            val noteId = note.id
            if (noteId != -1) {
                put(_ID, noteId )
            }
            put(TITLE,note.title)
            put(TEXT, note.text)
            put(IS_PINNED, note.isPinned)
            put(CREATED_AT, note.createdAt.time)
            put(UPDATED_AT, note.updatedAt!!.time)
        }


    }

    private fun fromNotes(notes: Array<out Note>): List<ContentValues> {
        val values = ArrayList<ContentValues>()
        for (note in notes) {
            values.add(fromNote(note))
        }
        return values
    }
}
