package com.capstone.schero.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.capstone.schero.data.database.DatabaseContract.ScholarshipColumns.Companion.ID
import com.capstone.schero.data.database.DatabaseContract.ScholarshipColumns.Companion.NAMA
import com.capstone.schero.data.database.DatabaseContract.ScholarshipColumns.Companion.TABLE_NAME

class BookmarksHelper(context: Context) {
    private var dbHelper = DatabaseHelper(context)
    private lateinit var db: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
        if (db.isOpen) db.close()
    }


    fun queryAll(): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID DESC"
        )
    }

    fun queryByName(namaBeasiswa: String): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            "$NAMA = ?",
            arrayOf(namaBeasiswa),
            null,
            null,
            null,
            null
        )
    }

    fun delete(namaBeasiswa: String): Int {
        return db.delete(DATABASE_TABLE, "$NAMA = '$namaBeasiswa'", null)
    }

    fun insert(values: ContentValues?): Long {
        return db.insert(DATABASE_TABLE, null, values)
    }

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: BookmarksHelper? = null
        fun getInstance(context: Context): BookmarksHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BookmarksHelper(context)
            }
    }
}