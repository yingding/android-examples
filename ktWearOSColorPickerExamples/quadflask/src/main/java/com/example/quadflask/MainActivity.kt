package com.example.quadflask

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import com.example.quadflask.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedColor: Int = 0// white


    override fun onCreate(savedInstanceState: Bundle?) {
        // Set dark theme actively
        // https://stackoverflow.com/questions/30174042/how-to-switch-themes-night-mode-without-restarting-the-activity
        setTheme(R.style.Theme_MyApp_Impl)

        super.onCreate(savedInstanceState)
        // init the default selected color
        val defaultColor = getColor(R.color.colorPickerInitial)
        selectedColor = defaultColor

        val displayHeight = Resources.getSystem().displayMetrics.heightPixels

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get initial color of color picker
        binding.colorPickerView.setInitialColor(selectedColor, false)
        // set text view to show chosen color so far
        binding.textViewSelectedColor.setBackgroundColor(selectedColor)

        binding.colorPickerView.addOnColorSelectedListener {
            selectedColor = it
            Log.v(TAG, "palette color selected:  0x${Integer.toHexString(selectedColor)}")
            // change the text view background
            binding.textViewSelectedColor.setBackgroundColor(selectedColor)
            // setFABColor(selectedColor)
            // scroll down to label of sliders
            binding.rootScrollView.post{
                binding.rootScrollView.scrollTo(displayHeight/2, binding.textViewSliderTitleLightness.top)
            }
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
            Toast.makeText(this, "Color is reset!", Toast.LENGTH_SHORT).show()
        }
        // called every time by on resume
        // resetSlidersColor(selectedColor)
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

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}