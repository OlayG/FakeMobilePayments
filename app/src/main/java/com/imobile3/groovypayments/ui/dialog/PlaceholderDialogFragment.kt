package com.imobile3.groovypayments.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.imobile3.groovypayments.R

class PlaceholderDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(it)

            builder
                .setTitle(R.string.common_under_construction)
                .setMessage(R.string.under_construction_alert_message)
                .setPositiveButton(R.string.common_acknowledged
                ) { _, _ ->
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}