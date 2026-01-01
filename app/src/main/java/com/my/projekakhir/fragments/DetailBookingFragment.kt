package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.FirebaseDatabase
import com.my.projekakhir.MainActivity
import com.my.projekakhir.databinding.FragmentDetailBookingBinding
import com.my.projekakhir.models.Booking
import com.my.projekakhir.viewModel.UserViewModel
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import com.my.projekakhir.NotificationReceiver
import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class DetailBookingFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentDetailBookingBinding? = null
    private val binding get() = _binding!!

    private val bookingRef = FirebaseDatabase.getInstance().getReference("bookings")

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
        }
    }

    private fun showBookingDetail() {
        // Ambil data dari arguments
        val services = arguments?.getStringArrayList("services") ?: arrayListOf()
        val total = arguments?.getInt("total", 0) ?: 0
        val tanggal = arguments?.getString("date") ?: "-"
        val jam = arguments?.getString("time") ?: "-"

        // Data pengguna dari ViewModel
        userViewModel.nama.observe(viewLifecycleOwner) { nama ->
            binding.tvNama.text = "Nama: $nama"
        }

        userViewModel.noHp.observe(viewLifecycleOwner) { noHP ->
            binding.tvNoHp.text = "No HP: $noHP"
        }

        // Data kendaraan (bisa diganti dengan data dinamis)
        binding.tvMobil.text = "Mobil: Toyota Avanza"
        binding.tvPlat.text = "Plat: B 1234 XYZ"

        // Tampilkan tanggal dan jam
        binding.tvTanggal.text = "Tanggal: $tanggal"
        binding.tvJam.text = "Jam: $jam"

        // Tampilkan layanan dan total
        binding.tvLayanan.text = "Layanan: ${services.joinToString(", ")}"
        binding.tvTotal.text = "Total: Rp ${"%,d".format(total)}"
    }

    private fun saveBooking() {
        val services = arguments?.getStringArrayList("services") ?: arrayListOf()
        val total = arguments?.getInt("total", 0) ?: 0
        val tanggal = arguments?.getString("date") ?: "-"
        val jam = arguments?.getString("time") ?: "-"

        val namaText = binding.tvNama.text.toString().replace("Nama: ", "")
        val hpText = binding.tvNoHp.text.toString().replace("No HP: ", "")
        val mobilText = binding.tvMobil.text.toString().replace("Mobil: ", "")
        val platText = binding.tvPlat.text.toString().replace("Plat: ", "")
        val catatan = binding.etNote.text.toString()

        if (namaText.isEmpty() || hpText.isEmpty()) {
            Toast.makeText(requireContext(), "Data pengguna tidak lengkap", Toast.LENGTH_SHORT).show()
            return
        }

        val booking = Booking(
            nama = namaText,
            hp = hpText,
            mobil = mobilText,
            plat = platText,
            tanggal = tanggal,
            jam = jam,
            layanan = services.joinToString(", "),
            total = "Rp ${"%,d".format(total)}",
            catatan = catatan
        )

        // ✅ SIMPAN SEKALI SAJA
        bookingRef.push().setValue(booking)
            .addOnSuccessListener {

                // 🔔 AKTIFKAN NOTIFIKASI
                setNotifikasi(tanggal, jam)

                Toast.makeText(
                    requireContext(),
                    "Booking berhasil & notifikasi diaktifkan",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()
                parentFragmentManager.popBackStack()
                (activity as? MainActivity)?.showBottomNav()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    "Gagal menyimpan booking: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun setNotifikasi(tanggal: String, jam: String) {

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val datetime = "$tanggal $jam"
        val jadwal = sdf.parse(datetime) ?: return

        val calendar = Calendar.getInstance()
        calendar.time = jadwal
        calendar.add(Calendar.MINUTE, -5)

        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        intent.putExtra("jam", jam)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // ✅ PALING AMAN
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}