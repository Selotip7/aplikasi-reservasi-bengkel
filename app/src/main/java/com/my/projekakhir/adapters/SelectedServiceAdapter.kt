package com.my.projekakhir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.projekakhir.databinding.ItemSelectedServiceBinding
import com.my.projekakhir.models.SelectedService

class SelectedServiceAdapter(
    private val services: List<SelectedService>
) : RecyclerView.Adapter<SelectedServiceAdapter.SelectedServiceViewHolder>() {

    inner class SelectedServiceViewHolder(private val binding: ItemSelectedServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: SelectedService) {
            binding.apply {
                tvServiceName.text = service.name
                tvDuration.text = "Estimasi: ${service.duration}"
                tvPrice.text = service.price
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedServiceViewHolder {
        val binding = ItemSelectedServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectedServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount() = services.size
}