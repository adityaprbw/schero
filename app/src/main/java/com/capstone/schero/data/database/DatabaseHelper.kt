package com.capstone.schero.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.capstone.schero.data.database.DatabaseContract.ScholarshipColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    companion object {
        private const val DATABASE_NAME = "bookmarks_database"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.ScholarshipColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.ScholarshipColumns.NAMA} TEXT NOT NULL," +
                "${DatabaseContract.ScholarshipColumns.GAMBAR} TEXT NOT NULL," +
                "${DatabaseContract.ScholarshipColumns.PRODI} TEXT NOT NULL," +
                "${DatabaseContract.ScholarshipColumns.NEGARA} TEXT NOT NULL," +
                "${DatabaseContract.ScholarshipColumns.IPK} TEXT NOT NULL," +
                "${DatabaseContract.ScholarshipColumns.JENJANG} TEXT NOT NULL)"
    }
}