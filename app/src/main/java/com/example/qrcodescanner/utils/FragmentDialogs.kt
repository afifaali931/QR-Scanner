package com.example.qrcodescanner.utils

import android.app.AlertDialog
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.qrcodescanner.R

fun Fragment.showPermissionRationaleDialog(
    onRetry: () -> Unit
) {
    AlertDialog.Builder(requireContext())
        .setTitle("Permissions Required")
        .setMessage("Camera and microphone permissions are required to scan QR codes.")
        .setCancelable(false)
        .setPositiveButton("Grant") { dialog, _ ->
            onRetry()
            dialog.dismiss()
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun Fragment.showScanInstructionsDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_scan_tips, null)
    val dialog = AlertDialog.Builder(requireContext())
        .setView(dialogView)
        .setCancelable(true)
        .create()

    dialogView.findViewById<Button>(R.id.btnGotIt).setOnClickListener {
        dialog.dismiss()
    }

    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.show()
}
