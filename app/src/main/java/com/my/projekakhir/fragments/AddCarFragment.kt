package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.my.projekakhir.databinding.FragmentAddCarBinding
import com.my.projekakhir.models.Car

class AddCarFragment : Fragment() {

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().getReference("cars")

        binding.btnSaveCar.setOnClickListener {

            val car = Car(
                brand = binding.etBrand.text.toString(),
                model = binding.etModel.text.toString(),
                year = binding.etYear.text.toString(),
                plateNumber = binding.etPlate.text.toString(),
                imageName = "avanza"
            )

            val ref = FirebaseDatabase
                .getInstance("https://pmob-d4529-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("cars")

            ref.push().setValue(car)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Mobil ditambahkan", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Gagal menambah mobil", Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
