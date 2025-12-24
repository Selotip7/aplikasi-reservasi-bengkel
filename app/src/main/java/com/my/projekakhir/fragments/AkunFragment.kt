package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.my.projekakhir.databinding.FragmentAkunBinding

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Profile
        binding.cardProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Buka Profil", Toast.LENGTH_SHORT).show()
        }

        // Account Settings
        binding.cardSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Pengaturan Akun", Toast.LENGTH_SHORT).show()
        }

        // Notifications
        binding.cardNotifications.setOnClickListener {
            Toast.makeText(requireContext(), "Notifikasi", Toast.LENGTH_SHORT).show()
        }

        // Saved Address
        binding.cardAddress.setOnClickListener {
            Toast.makeText(requireContext(), "Alamat Tersimpan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
