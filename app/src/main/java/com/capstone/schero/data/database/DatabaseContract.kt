package com.capstone.schero.data.database

import android.provider.BaseColumns

class DatabaseContract {
    internal class ScholarshipColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "bookmarks_scholarship"
            const val ID = "id"
            const val NAMA = "namaBeasiswa"
            const val GAMBAR = "linkGambar"
            const val PRODI = "prodi"
            const val NEGARA = "negara"
            const val IPK = "iPK"
            const val JENJANG = "jenjang"
        }
    }
}