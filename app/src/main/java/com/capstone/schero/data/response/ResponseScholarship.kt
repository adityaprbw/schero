package com.capstone.schero.data.response

import com.google.gson.annotations.SerializedName

data class ResponseScholarshipItem(

	@field:SerializedName("Pendidikan Terakhir")
	val pendidikanTerakhir: String,

	@field:SerializedName("Link Daftar")
	val linkDaftar: String,

	@field:SerializedName("Jenis Pendanaan")
	val jenisPendanaan: String,

	@field:SerializedName("IPK")
	val iPK: String,

	@field:SerializedName("Jenjang")
	val jenjang: String,

	@field:SerializedName("Benua")
	val benua: String,

	@field:SerializedName("Nama Beasiswa")
	val namaBeasiswa: String,

	@field:SerializedName("Prodi")
	val prodi: String,

	@field:SerializedName("Jenis Tes Bahasa")
	val jenisTesBahasa: String,

	@field:SerializedName("Biaya Hidup")
	val biayaHidup: String,

	@field:SerializedName("Fakultas")
	val fakultas: String,

	@field:SerializedName("Negara")
	val negara: String,

	@field:SerializedName("ID")
	val iD: String,

	@field:SerializedName("Skor")
	val skor: String
)
