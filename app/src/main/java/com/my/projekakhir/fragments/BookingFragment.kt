package com.my.projekakhir.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.my.projekakhir.MainActivity
import com.my.projekakhir.R
import com.my.projekakhir.databinding.FragmentBookingBinding
import androidx.fragment.app.activityViewModels
import com.my.projekakhir.viewModel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

private val HARGA_SERVIS_RINGAN = 150_000
private val HARGA_GANTI_OLI = 200_000
private val HARGA_SERVIS_BERKALA = 100_000
private val HARGA_SERVIS_REM = 80_000
private val HARGA_TUNE_UP = 120_000
private val HARGA_TAMBAL_BAN = 50_000

class BookingFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private var selectedTime: String = ""
    private val calendar = Calendar.getInstance()

    private var selectedDateRaw: String = "" // untuk sistem
    private var selectedDateDisplay: String = "" // untuk UI


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateTotalPrice() {
        var total = 0

        if (binding.cbServiceRingan.isChecked) {
            total += HARGA_SERVIS_RINGAN
        }

        if (binding.cbGantiOli.isChecked) {
            total += HARGA_GANTI_OLI
        }

        if (binding.cbServiceBerkala.isChecked) {
            total += HARGA_SERVIS_BERKALA
        }

        if (binding.cbServiceRem.isChecked) {
            total += HARGA_SERVIS_REM
        }

        if (binding.cbTuneUp.isChecked) {
            total += HARGA_TUNE_UP
        }

        if (binding.cbTambalBan.isChecked) {
            total += HARGA_TAMBAL_BAN
        }

        binding.tvTotalPrice.text = "Rp ${"%,d".format(total)}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe user data
        userViewModel.nama.observe(viewLifecycleOwner) { nama ->
            binding.tvNama.text = nama
        }

        userViewModel.noHp.observe(viewLifecycleOwner) { noHP ->
            binding.tvNoHp.text = noHP
        }

        // Setup checkbox listeners
        binding.cbServiceRingan.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        binding.cbGantiOli.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        binding.cbServiceBerkala.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        binding.cbServiceRem.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        binding.cbTuneUp.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        binding.cbTambalBan.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        // Setup date picker
        binding.tvTanggal.setOnClickListener {
            showDatePicker()
        }

        // Setup time picker
        binding.tvJam.setOnClickListener {
            showTimePicker()
        }

        // Setup buttons
        binding.btnConfirmBooking.setOnClickListener {
            openDetailBooking()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as? MainActivity)?.showBottomNav()
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                calendar.set(y, m, d)

                // FORMAT DATA (AMAN)
                val sdfRaw = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                selectedDateRaw = sdfRaw.format(calendar.time)

                // FORMAT TAMPILAN (INDONESIA)
                val sdfDisplay = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
                selectedDateDisplay = sdfDisplay.format(calendar.time)

                binding.tvTanggal.text = selectedDateDisplay
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun isTimeValid(selectedHour: Int, selectedMinute: Int): Boolean {
        val now = Calendar.getInstance()
        now.add(Calendar.MINUTE, 5) // batas minimal +5 menit

        val selected = Calendar.getInstance()
        selected.set(Calendar.HOUR_OF_DAY, selectedHour)
        selected.set(Calendar.MINUTE, selectedMinute)
        selected.set(Calendar.SECOND, 0)

        return selected.after(now) || selected == now
    }

    private fun showTimePicker() {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->

                if (!isTimeValid(selectedHour, selectedMinute)) {
                    Toast.makeText(
                        requireContext(),
                        "Pilih waktu minimal 5 menit dari sekarang",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TimePickerDialog
                }

                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                binding.tvJam.text = selectedTime
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }


    private fun getSelectedServices(): Pair<ArrayList<String>, Int> {
        val services = arrayListOf<String>()
        var total = 0

        if (binding.cbServiceRingan.isChecked) {
            services.add("Servis Ringan")
            total += HARGA_SERVIS_RINGAN
        }

        if (binding.cbGantiOli.isChecked) {
            services.add("Ganti Oli")
            total += HARGA_GANTI_OLI
        }

        if (binding.cbServiceBerkala.isChecked) {
            services.add("Servis Berkala")
            total += HARGA_SERVIS_BERKALA
        }

        if (binding.cbServiceRem.isChecked) {
            services.add("Servis Rem")
            total += HARGA_SERVIS_REM
        }

        if (binding.cbTuneUp.isChecked) {
            services.add("Tune Up")
            total += HARGA_TUNE_UP
        }

        if (binding.cbTambalBan.isChecked) {
            services.add("Tambal Ban")
            total += HARGA_TAMBAL_BAN
        }

        return Pair(services, total)
    }

    private fun openDetailBooking() {
        val (selectedServices, totalPrice) = getSelectedServices()

        // Validasi layanan
        if (selectedServices.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Pilih minimal satu layanan",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Validasi tanggal
        if (selectedDateRaw.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Pilih tanggal servis",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Validasi jam
        if (selectedTime.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Pilih jam servis",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Kirim data ke DetailBookingFragment
        val bundle = Bundle().apply {
            putStringArrayList("services", selectedServices)
            putInt("total", totalPrice)
            putString("date", selectedDateRaw)
            putString("date_display", selectedDateDisplay)

            putString("time", selectedTime)
        }

        val detailFragment = DetailBookingFragment()
        detailFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}