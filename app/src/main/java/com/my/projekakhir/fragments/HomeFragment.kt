package com.my.projekakhir.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.cardServisRem.setOnClickListener {
            navigateToBooking("Servis Rem")
        }

        binding.cardTuneUp.setOnClickListener {
            navigateToBooking("Tune Up")
        }

        binding.cardTambalBan.setOnClickListener {
            navigateToBooking("Tambal Ban")
        }
    }

    private fun setupSearch() {

        binding.searchService.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val keyword = s.toString().lowercase().trim()

                binding.cardGantiOli.visibility =
                    if (keyword.isEmpty() || keyword.contains("oli") || keyword.contains("ganti"))
                        View.VISIBLE else View.GONE

                binding.cardServisRingan.visibility =
                    if (keyword.isEmpty() || keyword.contains("ringan") || keyword.contains("servis"))
                        View.VISIBLE else View.GONE

                binding.cardServisBerkala.visibility =
                    if (keyword.isEmpty() || keyword.contains("berkala") || keyword.contains("servis"))
                        View.VISIBLE else View.GONE

                binding.cardServisRem.visibility =
                    if (keyword.isEmpty() || keyword.contains("servis") || keyword.contains("rem"))
                        View.VISIBLE else View.GONE

                binding.cardTuneUp.visibility =
                    if (keyword.isEmpty() || keyword.contains("tune") || keyword.contains("tune") || keyword.contains("up"))
                        View.VISIBLE else View.GONE

                binding.cardTambalBan.visibility =
                    if (keyword.isEmpty() || keyword.contains("tambal") || keyword.contains("ban"))
                        View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
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