package com.capstone.schero.data.di

import com.capstone.schero.data.repository.ScholarshipRepository

object Injection {
    fun provideRepository(): ScholarshipRepository {
        return ScholarshipRepository.getInstance()
    }
}
