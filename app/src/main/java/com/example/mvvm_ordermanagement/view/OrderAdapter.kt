package com.example.mvvm_ordermanagement.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_ordermanagement.databinding.OrderItemBinding
import com.example.mvvm_ordermanagement.model.Order

class OrderAdapter(private val orders: ArrayList<Order>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>()
{
    fun updateOrders(newOrders: ArrayList<Order>)
    {
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    inner class OrderViewHolder(val binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(order: Order)
        {
            binding.orderID.text = order.id.toString()
            binding.product.text = order.products.name
            binding.user.text = order.user.name
        }
    }
}