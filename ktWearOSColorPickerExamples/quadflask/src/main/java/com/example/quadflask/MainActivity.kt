package com.example.quadflask

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.DialogCompat
import androidx.fragment.app.FragmentActivity
import com.example.quadflask.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedColor: Int = 0// white
    private lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        // Set dark theme actively
        // https://stackoverflow.com/questions/30174042/how-to-switch-themes-night-mode-without-restarting-the-activity
        // setTheme(R.style.Theme_MyApp_Impl)

        super.onCreate(savedInstanceState)
        // init the default selected color
        val defaultColor = getColor(R.color.colorPickerInitial)
        selectedColor = defaultColor

        val displayHeight = Resources.getSystem().displayMetrics.heightPixels

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setInitialColor sets the color palette and selected color preview
        binding.colorPickerView.setInitialColor(selectedColor, false)
        // set text view to show chosen color so far
        binding.textViewSelectedColor.setBackgroundColor(selectedColor)

        dialog = MaterialAlertDialogBuilder(this, R.style.Theme_MyApp_Dialog)
            // .setBackground(ColorDrawable(Color.TRANSPARENT))
            // .setView(layoutInflater.inflate(R.layout.dialog_overlay, null))
            .setCancelable(true)
            .setPositiveButton(" ", null) // must have a space string to keep the first place, otherwise dialog shows only two button
            .setNegativeButton(R.string.text_again_cap, null)
            .setNeutralButton(R.string.text_go_on_cap) { _, _ ->
                // scroll down to label of sliders
                binding.rootScrollView.post {
                    binding.rootScrollView.scrollTo(
                        displayHeight / 2,
                        binding.textViewSliderTitleLightness.top
                    )
                }
            }
            .create()

        binding.rootScrollView.setOnScrollChangeListener(
            // Kotlin SAM
            View.OnScrollChangeListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int ->
                closeDialog()
            }
        )

        binding.colorPickerView.addOnColorSelectedListener {
            selectedColor = it
            Log.v(TAG, "palette color selected:  0x${Integer.toHexString(selectedColor)}")
            // change the text view background
            binding.textViewSelectedColor.setBackgroundColor(selectedColor)

            openDialog()
        }

        // this is called if the sliders are changed
        binding.colorPickerView.addOnColorChangedListener {
            selectedColor = it
            // change the text view
            binding.textViewSelectedColor.setBackgroundColor(selectedColor)
            // setFABColor(selectedColor)
            Log.v(TAG, "palette color changed: 0x${Integer.toHexString(selectedColor)}")
        }

        // use the cancel or reset fab to reset and scroll back the color
        binding.fabCancel.setOnClickListener {
            // scroll back to the first view element
            binding.rootScrollView.scrollTo(0, binding.textViewTitle.top)
            selectedColor = defaultColor
            resetColorPickerAndView(selectedColor)
            resetSlidersColor(selectedColor)
            Toast.makeText(this, R.string.text_color_is_reset, Toast.LENGTH_SHORT).show()
        }
        // called every time by on resume
        // resetSlidersColor(selectedColor)
    }

    private fun openDialog() {
        if (! dialog.isShowing) dialog.show()
    }

    private fun closeDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }

    /**
     * Looks strange
     * https://www.baeldung.com/kotlin/deprecation
     */
    @Deprecated("color picker shall not change FAB", level = DeprecationLevel.WARNING)
    private fun setFABColor(color: Int) {
        binding.fabSubmit.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun resetColorPickerAndView(color: Int) {
        // get initial color of color picker
        binding.colorPickerView.setColor(selectedColor, false)

        // set text view to show chosen color so far
        binding.textViewSelectedColor.setBackgroundColor(selectedColor)
    }

    private fun resetSlidersColor(color: Int) {
        /* https://github.com/QuadFlask/colorpicker/issues/69 */
        binding.vLightnessSlider.post {
            binding.vLightnessSlider.setColor(color)
        }
        binding.vAlphaSlider.post{
            binding.vAlphaSlider.setColor(color)
        }
    }

    override fun onResume() {
        super.onResume()
        resetSlidersColor(selectedColor)
    }

    override fun onPause() {
        super.onPause()
        closeDialog()
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}