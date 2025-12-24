package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.my.projekakhir.R
import com.my.projekakhir.adapters.CarAdapter
import com.my.projekakhir.databinding.FragmentKendaraanBinding
import com.my.projekakhir.models.Car

class KendaraanFragment : Fragment() {

    private var _binding: FragmentKendaraanBinding? = null
    private val binding get() = _binding!!

    private val carList = mutableListOf<Car>()
    private lateinit var carAdapter: CarAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKendaraanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance()
            .getReference("cars")

        setupRecyclerView()
        loadCarsFromFirebase()

        binding.btnAddCar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddCarFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        carAdapter = CarAdapter(
            carList,
            onHistoryClick = {
                Toast.makeText(requireContext(), "Riwayat ${it.model}", Toast.LENGTH_SHORT).show()
            },
            onDetailClick = {
                Toast.makeText(requireContext(), "Detail ${it.model}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.carsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = carAdapter
        }
    }

    private fun loadCarsFromFirebase() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                carList.clear()

                for (child in snapshot.children) {
                    val car = child.getValue(Car::class.java)
                    if (car != null) {
                        carList.add(car)
                    }
                }

                carAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
