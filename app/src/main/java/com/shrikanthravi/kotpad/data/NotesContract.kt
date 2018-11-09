package com.shrikanthravi.kotpad.data

import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.CREATED_AT
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.IS_PINNED
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.TEXT
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.TITLE
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable.UPDATED_AT
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable._ID
import com.shrikanthravi.kotpad.data.NotesContract.NotesTable._TABLE_NAME

object NotesContract{
    object NotesTable{
        val _ID = "_id"
        val _TABLE_NAME = "notes"
        val TITLE = "title"
        val TEXT = "text"
        val IS_PINNED = "is_pinned"
        val CREATED_AT = "created_at"
        val UPDATED_AT = "updated_at"
    }

    val SQL_CREATE_ENTRIES = """CREATE TABLE $_TABLE_NAME (
            $_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            $TITLE TEXT,
            $TEXT TEXT,
            $IS_PINNED INTEGER,
            $CREATED_AT INTEGER,
            $UPDATED_AT INTEGER)""".trimMargin()

    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $_TABLE_NAME"

    val SQL_QUERY_ALL =  "SELECT * FROM $_TABLE_NAME ORDER BY $CREATED_AT DESC"

}