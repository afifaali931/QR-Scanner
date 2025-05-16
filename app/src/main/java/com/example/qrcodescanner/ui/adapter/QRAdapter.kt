package com.example.qrcodescanner.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.model.QRItem

class QRAdapter(private val items: List<QRItem>, private val onItemClick: (QRItem) -> Unit) : RecyclerView.Adapter<QRAdapter.QRViewHolder>() {

    inner class QRViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.iconImage)
        val label: TextView = itemView.findViewById(R.id.labelText)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qr, parent, false)
        return QRViewHolder(view)
    }
    override fun onBindViewHolder(holder: QRViewHolder, position: Int) {
        val item = items[position]
        holder.icon.setImageResource(item.iconResId)
        holder.label.text = item.title

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
    override fun getItemCount() = items.size
}