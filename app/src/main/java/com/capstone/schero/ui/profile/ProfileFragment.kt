package com.capstone.schero.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.schero.databinding.FragmentProfileBinding
import com.capstone.schero.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            val userPhotoUrl = user.photoUrl
            val userName = user.displayName
            val userEmail = user.email

            Glide.with(this)
                .load(userPhotoUrl)
                .into(binding.profileImage)
            binding.apply {
                fullName.text = userName
                email.setText(userEmail)
                signOutButton.setOnClickListener {
                    auth.signOut()
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}