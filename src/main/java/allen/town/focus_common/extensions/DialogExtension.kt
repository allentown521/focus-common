/*
 * Copyright (c) 2020 Hemanth Savarla.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package code.name.monkey.retromusic.extensions

import allen.town.focus_common.R
import allen.town.focus_common.extensions.accentTextColor
import allen.town.focus_common.views.AccentMaterialDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun DialogFragment.materialDialog(title: Int): MaterialAlertDialogBuilder {
    return AccentMaterialDialog(
        requireContext(),
        R.style.MaterialAlertDialogTheme
    ).setTitle(title)
}

fun AlertDialog.colorButtons(context: Context): AlertDialog {
    setOnShowListener {
        getButton(AlertDialog.BUTTON_POSITIVE).accentTextColor(context)
        getButton(AlertDialog.BUTTON_NEGATIVE).accentTextColor(context)
        getButton(AlertDialog.BUTTON_NEUTRAL).accentTextColor(context)
    }
    return this
}

fun Fragment.materialDialog(): MaterialDialog {
    return MaterialDialog(requireContext())
        .cornerRadius(res = R.dimen.m3_dialog_corner_size)
}
