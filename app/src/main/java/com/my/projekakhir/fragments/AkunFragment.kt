package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.my.projekakhir.R
import com.my.projekakhir.databinding.FragmentAkunBinding
import com.my.projekakhir.viewModel.UserViewModel
import kotlin.getValue

class AkunFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
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


        userViewModel.nama.observe(viewLifecycleOwner) { nama ->
            binding.tvNama.text = nama
        }

        userViewModel.email.observe(viewLifecycleOwner) { email ->
            binding.tvEmail.text = email
        }

        userViewModel.noHp.observe(viewLifecycleOwner) { noHP ->
            binding.tvNoHp.text = noHP
        }

        // PROFILE
        binding.cardProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfilFragment())
                .addToBackStack(null)
                .commit()
        }

        // PENGATURAN AKUN
        binding.cardSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PengaturanAkunFragment())
                .addToBackStack(null)
                .commit()
        }

        // NOTIFIKASI
        binding.cardNotifications.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NotifikasiFragment())
                .addToBackStack(null)
                .commit()
        }

        // ALAMAT TERSIMPAN
        binding.cardAddress.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AlamatTersimpanFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
