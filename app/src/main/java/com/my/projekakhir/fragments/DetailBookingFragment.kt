package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.my.projekakhir.MainActivity
import com.my.projekakhir.databinding.FragmentDetailBookingBinding
import com.my.projekakhir.models.Booking

class DetailBookingFragment : Fragment() {

    private var _binding: FragmentDetailBookingBinding? = null
    private val binding get() = _binding!!

    private val bookingRef =
        FirebaseDatabase.getInstance().getReference("bookings")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBookingDetail()

        binding.btnConfirmBooking.setOnClickListener {
            saveBooking()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as? MainActivity)?.showBottomNav()
        }
    }

    private fun showBookingDetail() {
        val nama = arguments?.getString("nama") ?: "-"
        val hp = arguments?.getString("hp") ?: "-"
        val mobil = arguments?.getString("mobil") ?: "-"
        val plat = arguments?.getString("plat") ?: "-"
        val services = arguments?.getStringArrayList("services") ?: arrayListOf()
        val total = arguments?.getInt("total", 0) ?: 0

        // Data pengguna
        binding.tvNama.text = nama
        binding.tvHp.text = hp

        // Data kendaraan
        binding.tvMobil.text = mobil
        binding.tvPlat.text = plat

        // Layanan & total
        binding.tvLayanan.text = services.joinToString(", ")
        binding.tvTotal.text = "Rp ${total}"
    }

    private fun saveBooking() {
        val nama = binding.tvNama.text.toString()
        val hp = binding.tvHp.text.toString()
        val mobil = binding.tvMobil.text.toString()
        val plat = binding.tvPlat.text.toString()
        val layanan = binding.tvLayanan.text.toString()
        val total = binding.tvTotal.text.toString()
        val catatan = binding.etNote.text.toString()

        val booking = Booking(
            nama = nama,
            hp = hp,
            mobil = mobil,
            plat = plat,
            layanan = layanan,
            total = total,
            catatan = catatan
        )

        bookingRef.push().setValue(booking)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Booking berhasil disimpan", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
                (activity as? MainActivity)?.showBottomNav()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal menyimpan booking", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}