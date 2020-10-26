package com.trilema.doctrinalang.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.trilema.doctrinalang.R

class AboutFragmet : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.about_fragment, null))
                .setCancelable(true)
                .setNegativeButton("OK") { dialog, id -> dialog!!.cancel() }
        return builder.create()
    }
}