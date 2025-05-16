package com.example.qrcodescanner.ui.adapter.savedqrcode

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qrcodescanner.R
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import com.example.qrcodescanner.databinding.ItemQrHistoryBinding

class HistoryAdapter(
    private val onBookmarkClick: (SavedQrCodeEntity) -> Unit,
    private val onDeleteClick: (SavedQrCodeEntity) -> Unit,
    private val onItemClick: (SavedQrCodeEntity) -> Unit
) : ListAdapter<SavedQrCodeEntity, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    inner class HistoryViewHolder(private val binding: ItemQrHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedQrCodeEntity) = with(binding) {


            Glide.with(itemView.context)
                .load(item.imagePath)
                .placeholder(R.drawable.ic_scan_round)
                .into(binding.ivQrImage)

            tvQrTitle.text = item.qrType
            tvQrSubtitle.text = item.content

            binding.root.setOnClickListener {
                onItemClick(item)
            }

            ivMore.setOnClickListener {
                val popup = PopupMenu(it.context, ivMore)
                popup.menuInflater.inflate(R.menu.menu_qr_options, popup.menu)

                popup.menu.findItem(R.id.action_bookmark).title =
                    if (item.isBookmarked) "Remove Bookmark" else "Bookmark"

                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_bookmark -> {
                            onBookmarkClick(item)
                            true
                        }
                        R.id.action_delete -> {
                            onDeleteClick(item)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemQrHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<SavedQrCodeEntity>() {
        override fun areItemsTheSame(oldItem: SavedQrCodeEntity, newItem: SavedQrCodeEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SavedQrCodeEntity, newItem: SavedQrCodeEntity) =
            oldItem == newItem
    }
}
