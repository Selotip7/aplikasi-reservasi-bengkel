package com.my.projekakhir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.projekakhir.databinding.ItemServiceCardBinding
import com.my.projekakhir.models.Service

class ServiceAdapter(
    private val services: List<Service>,
    private val onItemClick: (Service) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(private val binding: ItemServiceCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.apply {
                tvServiceName.text = service.name
                tvLastService.text = service.lastService
                tvStatus.text = service.status
                tvPrice.text = service.price

                // Set status background color
                tvStatus.setBackgroundColor(itemView.context.getColor(service.statusColor))
                tvStatus.setTextColor(itemView.context.getColor(service.statusTextColor))

                root.setOnClickListener {
                    onItemClick(service)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount() = services.size
}