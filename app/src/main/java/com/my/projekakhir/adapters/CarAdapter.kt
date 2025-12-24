package com.my.projekakhir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.projekakhir.R
import com.my.projekakhir.databinding.ItemCarCardBinding
import com.my.projekakhir.models.Car

class CarAdapter(
    private val cars: List<Car>,
    private val onHistoryClick: (Car) -> Unit,
    private val onDetailClick: (Car) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(private val binding: ItemCarCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car) {
            binding.tvCarName.text = "${car.brand} ${car.model}"
            binding.tvCarYear.text = car.year
            binding.tvPlateNumber.text = car.plateNumber

            val context = binding.root.context
            val imageResId = context.resources.getIdentifier(
                car.imageName,
                "drawable",
                context.packageName
            )

            if (imageResId != 0) {
                binding.ivCarImage.setImageResource(imageResId)
            } else {
                binding.ivCarImage.setImageResource(R.drawable.placeholder_car)
            }

            binding.btnHistory.setOnClickListener { onHistoryClick(car) }
            binding.btnDetail.setOnClickListener { onDetailClick(car) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount() = cars.size
}