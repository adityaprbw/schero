package com.capstone.schero.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capstone.schero.databinding.ActivityDetailBinding
import com.capstone.schero.ui.MainViewModel
import com.capstone.schero.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {

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
                namaTextView.text = list[0].namaBeasiswa

                negaraTextView.text = list[0].negara

                val uniqueJenjangList = list.distinctBy { it.jenjang }
                val jenjangText = StringBuilder()
                for (jenjang in uniqueJenjangList) jenjangText.append("${jenjang.jenjang}\n")
                jenjangTextView.text = jenjangText.toString()

                pendanaanTextView.text = list[0].jenisPendanaan

                biayaTextView.text = list[0].biayaHidup

                ipkTextView.text = list[0].iPK

                if (list[0].jenisTesBahasa == "0") jenisTesTextView.visibility = View.VISIBLE
                else jenisTesTextView.text = list[0].jenisTesBahasa

                if (list[0].skor == "0") skorTextView.visibility = View.VISIBLE
                else skorTextView.text = list[0].skor

                val fakultasText = StringBuilder()
                for (fakultas in list) fakultasText.append("${fakultas.fakultas}\n")
                fakultasTextView.text = fakultasText.toString()

                val prodiText = StringBuilder()
                for (prodi in list) prodiText.append("${prodi.prodi}\n")
                prodiTextView.text = prodiText.toString()

                linkCardView.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(list[0].linkDaftar)
                    startActivity(openURL)
                }
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}