package com.my.projekakhir.fragments

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
            total += 150000
        }

        if (binding.cbGantiOli.isChecked) {
            total += 200000
        }

        if (binding.cbServiceBerkala.isChecked) {
            total += 100000
        }

        if (binding.cbServiceRem.isChecked) {
            total += 80000
        }

        if (binding.cbTuneUp.isChecked) {
            total += 120000
        }

        if (binding.cbTambalBan.isChecked) {
            total += 50000
        }

        binding.tvTotalPrice.text = "Rp ${"%,d".format(total)}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel.nama.observe(viewLifecycleOwner) { nama ->
            binding.tvNama.text = nama
        }

        userViewModel.noHp.observe(viewLifecycleOwner) { noHP ->
            binding.tvNoHp.text = noHP
        }

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

        binding.btnConfirmBooking.setOnClickListener {
            openDetailBooking()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as? MainActivity)?.showBottomNav()
        }


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

        if (selectedServices.isEmpty()) {
            Toast.makeText(requireContext(),
                "Pilih minimal satu layanan",
                Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle().apply {
            putStringArrayList("services", selectedServices)
            putInt("total", totalPrice)
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
