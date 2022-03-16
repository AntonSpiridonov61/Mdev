package com.example.wearher

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels


class MyDialog(val ctx: Context): DialogFragment() {
    private val dataModel: DataModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var choice = 0
        var types: Array<String> = ctx.resources.getStringArray(R.array.weather_types)
        return ctx.let { AlertDialog.Builder(it)
            .setTitle("Select type")
            .setSingleChoiceItems(types, -1) {
                    dialog, id -> choice = id
            }
            .setPositiveButton("Select") {
                dialog, id -> dataModel.selectType.value = types[choice]
            }
            .setNegativeButton("Cancel", null)
            .create()
        }
    }
}