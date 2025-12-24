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

class BookingFragment : Fragment() {

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

        binding.tvTotalPrice.text = "Rp ${"%,d".format(total)}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.cbServiceRingan.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        binding.cbGantiOli.setOnCheckedChangeListener { _, _ ->
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
            total += 150000
        }

        if (binding.cbGantiOli.isChecked) {
            services.add("Ganti Oli")
            total += 200000
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
