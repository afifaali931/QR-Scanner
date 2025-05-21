package com.example.qrcodescanner.ui.adapter.style

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.databinding.ItemStyleBinding
import com.example.qrcodescanner.model.QrStyleModel

class StyleAdapter(
    private val styles: List<QrStyleModel>,
    private val onStyleSelected: (QrStyleModel) -> Unit
) : RecyclerView.Adapter<StyleAdapter.StyleViewHolder>() {

    inner class StyleViewHolder(val binding: ItemStyleBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleViewHolder {
        val binding = ItemStyleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StyleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StyleViewHolder, position: Int) {
        var style = styles[position]

        val preview = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas  = Canvas(preview)

        canvas.drawColor(style.backgroundColor)


        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                0f, 0f,
                preview.width.toFloat(),
                preview.height.toFloat(),
                style.fgStartColor,
                style.fgEndColor,
                Shader.TileMode.CLAMP
            )
//            style = Paint.Style.FILL
        }

        val inset = 30f
        canvas.drawRect(
            inset,
            inset,
            preview.width - inset,
            preview.height - inset,
            paint
        )

        holder.binding.imageViewStyle.setImageBitmap(preview)

        holder.binding.root.setOnClickListener {
            onStyleSelected(style)
        }
    }

    override fun getItemCount() = styles.size

}