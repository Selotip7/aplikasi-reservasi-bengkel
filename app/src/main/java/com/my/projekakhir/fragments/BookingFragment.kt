package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.my.projekakhir.MainActivity
import com.my.projekakhir.databinding.FragmentBookingBinding
import com.my.projekakhir.models.Booking

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private val bookingRef =
        FirebaseDatabase.getInstance().getReference("bookings")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnConfirmBooking.setOnClickListener {
            saveBooking()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as? MainActivity)?.showBottomNav()
        }
    }

    private fun saveBooking() {
        val serviceName = arguments?.getString("SERVICE_NAME") ?: "Servis Umum"

        val booking = Booking(serviceName)

        bookingRef.push().setValue(booking).addOnSuccessListener {
            Toast.makeText(requireContext(), "Booking berhasil!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            (activity as? MainActivity)?.showBottomNav()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
