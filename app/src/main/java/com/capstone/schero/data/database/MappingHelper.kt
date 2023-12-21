package com.capstone.schero.data.database

import android.database.Cursor
import com.capstone.schero.data.response.BookmarksScholarship
class MappingHelper {
    companion object {
        fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<BookmarksScholarship> {
            val bookmarkList = ArrayList<BookmarksScholarship>()
            favCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.ID))
                    val nama = getString(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.NAMA))
                    val gambar = getString(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.GAMBAR))
                    val prodi = getString(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.PRODI))
                    val negara = getString(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.NEGARA))
                    val ipk = getString(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.IPK))
                    val jenjang = getString(getColumnIndexOrThrow(DatabaseContract.ScholarshipColumns.JENJANG))
                    bookmarkList.add(BookmarksScholarship(id, nama, gambar, prodi, negara, ipk, jenjang))
                }
            }
            return bookmarkList
        }
    }
}