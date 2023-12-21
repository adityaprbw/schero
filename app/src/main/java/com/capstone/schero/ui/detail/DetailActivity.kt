package com.capstone.schero.ui.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.schero.R
import com.capstone.schero.data.database.BookmarksHelper
import com.capstone.schero.data.database.DatabaseContract
import com.capstone.schero.databinding.ActivityDetailBinding
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private var isExist = false
    private var position: Int = 0
    private lateinit var favHelper: BookmarksHelper
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityDetailBinding
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.searchScholarship(name!!)

        viewModel.search.observe(this) { list ->

            binding.apply {

                Glide.with(this@DetailActivity)
                    .load(list[0].linkGambar)
                    .into(scholarshipPhoto)

                namaScholarship.text = list[0].namaBeasiswa

                negaraTextView.text = list[0].negara

                val uniqueJenjangList = list.distinctBy { it.jenjang }
                val jenjangText = StringBuilder()
                for (jenjang in uniqueJenjangList) jenjangText.append("${jenjang.jenjang}\n")
                jenjangTextView.text = jenjangText.toString()

                pendanaanTextView.text = list[0].jenisPendanaan

                biayaTextView.text = list[0].biayaHidup

                if (list[0].iPK == "0") ipkTextView.visibility = View.VISIBLE
                else ipkTextView.text = list[0].iPK

                if (list[0].jenisTesBahasa == "0") jenisTesTextView.visibility = View.VISIBLE
                else jenisTesTextView.text = list[0].jenisTesBahasa

                if (list[0].skor == "0") skorTextView.visibility = View.VISIBLE
                else skorTextView.text = list[0].skor

                val uniqueFakultasList = list.distinctBy { it.fakultas }
                val fakultasText = StringBuilder()
                for (fakultas in uniqueFakultasList) fakultasText.append("${fakultas.fakultas}\n")
                fakultasTextView.text = fakultasText.toString()

                val uniqueProdiList = list.distinctBy { it.prodi }
                val prodiText = StringBuilder()
                for (prodi in uniqueProdiList) prodiText.append("${prodi.prodi}\n")
                prodiTextView.text = prodiText.toString()

                linkCardView.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(list[0].linkDaftar)
                    startActivity(openURL)
                }

                favHelper = BookmarksHelper.getInstance(applicationContext)
                favHelper.open()
                val cursor = favHelper.queryByName(name)
                if (cursor != null && cursor.moveToFirst()) {
                    position = intent.getIntExtra(EXTRA_POSITION, 0)
                    toggleBookmark.isChecked = true
                    isExist = true
                } else {
                    toggleBookmark.isChecked = false
                    isExist = false
                }

                val values = ContentValues()
                values.put(DatabaseContract.ScholarshipColumns.NAMA, list[0].namaBeasiswa)
                values.put(DatabaseContract.ScholarshipColumns.GAMBAR, list[0].linkGambar)
                values.put(DatabaseContract.ScholarshipColumns.PRODI, list[0].prodi)
                values.put(DatabaseContract.ScholarshipColumns.NEGARA, list[0].negara)
                values.put(DatabaseContract.ScholarshipColumns.IPK, list[0].iPK)
                values.put(DatabaseContract.ScholarshipColumns.JENJANG, list[0].jenjang)

                if (isExist) {
                    toggleBookmark.setOnClickListener {
                        favHelper.delete(list[0].namaBeasiswa)
                        toggleBookmark.isChecked = false
                        showToast(getString(R.string.delete))
                        finish()
                    }
                } else {
                    toggleBookmark.setOnClickListener {
                        favHelper.insert(values)
                        toggleBookmark.isChecked = true
                        showToast(getString(R.string.add))
                        finish()
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_POSITION = "extra_position"
    }
}