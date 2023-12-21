package com.capstone.schero.data.response

import com.google.gson.annotations.SerializedName

data class BookmarksScholarship(

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("Nama Beasiswa")
    val namaBeasiswa: String,

    @field:SerializedName("Link Gambar")
    val linkGambar: String,

    @field:SerializedName("Prodi")
    val prodi: String,

    @field:SerializedName("Negara")
    val negara: String,

    @field:SerializedName("IPK")
    val iPK: String,

    @field:SerializedName("Jenjang")
    val jenjang: String,
)