package com.example.ayushrajcelebrare

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

lateinit var canvas: Canvas
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        canvas = findViewById(R.id.canvas)

        val addButton = findViewById<Button>(R.id.addBtn)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        addButton.setOnClickListener {
            canvas.addNewText()
        }
        val editText = findViewById<EditText>(R.id.editText)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                canvas.updateSelectedText(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Listen for changes in the SeekBar and update text size accordingly
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val textSize = (progress + 20).toFloat() // Adding a base value to avoid very small text
                canvas.changeTextSize(textSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        val colorButton = findViewById<Button>(R.id.clrBtn)
        colorButton.setOnClickListener {
            showColorPickerDialog()
        }

    }

    private fun showColorPickerDialog() {
        ColorPickerDialog.Builder(this)
            .setTitle("ColorPicker Dialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton("Confirm",
                ColorEnvelopeListener { envelope, fromUser ->
//                    setLayoutColor(envelope)
             canvas.changeTextColor(Integer.parseInt(envelope.color.toString()))
                })
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, i -> dialogInterface.dismiss() }
            .attachAlphaSlideBar(true) // the default value is true.
            .attachBrightnessSlideBar(true) // the default value is true.
            .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
            .show()
    }
}