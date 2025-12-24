package com.my.projekakhir


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.projekakhir.databinding.FragmentDetailKendaraanBinding
import com.my.projekakhir.R

class DetailKendaraanFragment : Fragment() {

    private var _binding: FragmentDetailKendaraanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailKendaraanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari Bundle
        val brand = arguments?.getString("brand")
        val model = arguments?.getString("model")
        val year = arguments?.getString("year")
        val plate = arguments?.getString("plate")
        val imageName = arguments?.getString("image")

        // Set ke UI
        binding.tvCarName.text = "$brand $model"
        binding.tvCarYear.text = year
        binding.tvPlateNumber.text = plate

        val imageResId = requireContext().resources.getIdentifier(
            imageName,
            "drawable",
            requireContext().packageName
        )

        if (imageResId != 0) {
            binding.ivCarImage.setImageResource(imageResId)
        } else {
            binding.ivCarImage.setImageResource(R.drawable.placeholder_car)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
