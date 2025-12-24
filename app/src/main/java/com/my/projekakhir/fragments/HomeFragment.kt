package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.projekakhir.MainActivity
import com.my.projekakhir.R
import com.my.projekakhir.adapters.ServiceAdapter
import com.my.projekakhir.databinding.FragmentHomeBinding
import com.my.projekakhir.models.Service

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var serviceAdapter: ServiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupMainButtons()
        setupServiceCategoryClicks()
        setupSearch()
    }

    private fun setupRecyclerView() {
        val services = listOf(
            Service(
                id = 1,
                name = "Ganti Oli Mesin",
                status = "Perlu Servis",
                price = "Rp 150.000",
                lastService = "3 bulan lalu",
                statusColor = R.color.red_100,
                statusTextColor = R.color.red_700
            ),
            Service(
                id = 2,
                name = "Cek Rem",
                status = "Disarankan",
                price = "Rp 200.000",
                lastService = "5 bulan lalu",
                statusColor = R.color.orange_100,
                statusTextColor = R.color.orange_700
            ),
            Service(
                id = 3,
                name = "Tune Up",
                status = "Disarankan",
                price = "Rp 350.000",
                lastService = "6 bulan lalu",
                statusColor = R.color.orange_100,
                statusTextColor = R.color.orange_700
            )
        )

        serviceAdapter = ServiceAdapter(services) {
            navigateToBooking(null)
        }

        binding.recommendedServicesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceAdapter
        }
    }

    private fun setupMainButtons() {
        binding.btnBookingNow.setOnClickListener {
            navigateToBooking(null)
        }
    }

    private fun setupServiceCategoryClicks() {
        binding.cardGantiOli.setOnClickListener {
            navigateToBooking("Ganti Oli")
        }

        binding.cardServisRingan.setOnClickListener {
            navigateToBooking("Servis Ringan")
        }

        binding.cardServisBerkala.setOnClickListener {
            navigateToBooking("Servis Berkala")
        }
    }

    private fun setupSearch() {
        binding.searchService.setOnEditorActionListener { _, _, _ ->
            val keyword = binding.searchService.text.toString()
            navigateToBooking(keyword)
            true
        }
    }

    private fun navigateToBooking(serviceName: String?) {
        val fragment = BookingFragment().apply {
            arguments = Bundle().apply {
                putString("SERVICE_NAME", serviceName)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        (activity as? MainActivity)?.hideBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
