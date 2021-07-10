package com.loskon.androidprojectcitizens.ui.sheets

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.loskon.androidprojectcitizens.R
import com.loskon.androidprojectcitizens.R.style.BaseBottomSheetDialog
import com.loskon.androidprojectcitizens.ui.fragment.SettingsFragment
import com.loskon.androidprojectcitizens.utils.showKeyboard

/**
 * Нижнее диалоговое окно для ввода значений
 */

class SheetDialog(
    private val context: Context,
    private val settingsFragment: SettingsFragment
) {

    companion object {
        const val TYPE_PERIOD = "type_period"
        const val TYPE_RANGE_MIN = "type_range_min"
        const val TYPE_RANGE_MAX = "type_range_max"
    }

    private val dialog: BottomSheetDialog = BottomSheetDialog(context, BaseBottomSheetDialog)
    private val behavior: BottomSheetBehavior<FrameLayout> = dialog.behavior
    private val view = View.inflate(context, R.layout.sheet_dialog_settings, null)

    private val inputLayout: TextInputLayout = view.findViewById(R.id.text_input_layout)
    private val editText: TextInputEditText = view.findViewById(R.id.text_input_edit_text)
    private val btnOk: MaterialButton = view.findViewById(R.id.btn_sheet_ok)
    private val btnCancel: MaterialButton = view.findViewById(R.id.btn_sheet_cancel)

    private var min: Int = 0
    private var max: Int = 0
    private var oldNeighboringValue: Int = 0
    private var typeSlider: String = ""

    init {
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
            isHideable = false
        }
        editText.showKeyboard(context)
    }

    fun show(type: String) {
        typeSlider = type
        buildingSheetDialog()
    }

    fun show(type: String, oppValue: Int) {
        typeSlider = type
        oldNeighboringValue = oppValue
        buildingSheetDialog()
    }

    private fun buildingSheetDialog() {
        dialog.setContentView(view)

        installHandlers()
        setupMinAndMaxValues()
        setupHint()

        dialog.show()
    }

    private fun installHandlers() {
        editText.doOnTextChanged { _, _, _, _ ->
            run {
                errorManagement()
            }
        }

        btnOk.setOnClickListener {
            checkingEnteredValue()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun errorManagement() {
        if (inputLayout.error != null) {
            inputLayout.isErrorEnabled = false
        }
    }

    private fun checkingEnteredValue() {
        val text: String = editText.text.toString().trim()
        val enteredValue: Int = text.getNumberCharacters()

        val hasInvalidValue: Boolean = (text.isEmpty()) or
                (enteredValue < min) or (enteredValue > max)

        if (hasInvalidValue) {
            callingErrorMessage()
        } else {
            setEnteredValue(enteredValue)
        }
    }

    private fun callingErrorMessage() {
        inputLayout.apply {
            error = context.getString(R.string.sh_settings_error)
            isErrorEnabled = true
        }
    }

    private fun String.getNumberCharacters(): Int {
        return if (isNotEmpty()) {
            toInt()
        } else {
            0
        }
    }

    private fun setEnteredValue(enteredValue: Int) {

        val newNeighboringValue: Int = when {
            (typeSlider == TYPE_RANGE_MIN) and (enteredValue >= oldNeighboringValue) -> {
                enteredValue + 1
            }
            (typeSlider == TYPE_RANGE_MAX) and (oldNeighboringValue >= enteredValue) -> {
                enteredValue - 1
            }
            else -> {
                oldNeighboringValue
            }
        }

        settingsFragment.setSlidersValues(typeSlider, enteredValue, newNeighboringValue)

        dialog.dismiss()
    }

    private fun setupMinAndMaxValues() {
        when (typeSlider) {

            TYPE_PERIOD -> {
                min = getValueInteger(R.integer.min_val_generation_period)
                max = getValueInteger(R.integer.max_val_generation_period)
            }

            TYPE_RANGE_MIN -> {
                min = getValueInteger(R.integer.min_val_range_min)
                max = getValueInteger(R.integer.max_val_range_min) - 1
            }

            TYPE_RANGE_MAX -> {
                min = getValueInteger(R.integer.min_val_range_min) + 1
                max = getValueInteger(R.integer.max_val_range_min)
            }
        }
    }

    private fun getValueInteger(integerId: Int) = context.resources.getInteger(integerId)

    private fun setupHint() {
        inputLayout.hint = context.getString(R.string.sh_settings_hint, min, max)
    }
}