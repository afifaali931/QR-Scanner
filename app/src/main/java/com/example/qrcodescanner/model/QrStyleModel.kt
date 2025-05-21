package com.example.qrcodescanner.model


data class QrStyleModel(
    val fgStartColor: Int,
    val fgEndColor: Int,
    val backgroundColor: Int,
    val cornerStyle: CornerStyle = CornerStyle.SQUARE
)


enum class CornerStyle {
    SQUARE, ROUNDED, DOT
}
