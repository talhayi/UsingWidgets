package com.example.usingwidgets2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.usingwidgets2.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var control = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonImage1.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.image1)
        }

        binding.buttonImage2.setOnClickListener {
            binding.imageView.setImageResource(resources.getIdentifier("image2","drawable", packageName))
        }

        binding.switch1.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                Log.e("Result","ON")
            }else {
                Log.e("Result", "OFF")
            }
        }

        binding.buttonStart.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
        }

        binding.buttonStop.setOnClickListener {
            binding.progressBar.visibility = View.INVISIBLE
        }

        binding.slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.textViewResult.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        val countries = ArrayList<String>()
        countries.add("Turkey")
        countries.add("Italy")
        countries.add("Spain")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, countries)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            control = isChecked
            if (control){
                val chooseButton = findViewById<Button>(checkedId)
                val buttonText = chooseButton.text.toString()
                Log.e("Result", "Category       : $buttonText")
            }
        }

        binding.buttonTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.show(supportFragmentManager,"")
            timePicker.addOnPositiveButtonClickListener {
                binding.editTextTime.setText("${timePicker.hour} : ${timePicker.minute}")
            }

        }

        binding.buttonDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build()

            datePicker.show(supportFragmentManager,"")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
                val date = dateFormat.format(it)//date info -> ms type
                binding.editTextDate.setText(date)
            }
        }

        binding.buttonToast.setOnClickListener {
            Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show()
        }

        binding.buttonSnackbar.setOnClickListener {
            Snackbar.make(it,"Do you want to delete?",Snackbar.LENGTH_SHORT)
                .setAction("Yes"){view->
                    Snackbar.make(view,"Deleted",Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.WHITE)
                        .setTextColor(Color.RED)
                        .show()
                }
                .setBackgroundTint(Color.WHITE)
                .setTextColor(Color.BLUE)
                .setActionTextColor(Color.RED)
                .show()
        }

        binding.buttonAlert.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Title")
                .setMessage("Content")
                .setPositiveButton("OK"){_,_ ->
                    Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel"){_,_ ->
                    Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        binding.buttonShow.setOnClickListener {
            Log.e("Result", "Switch State   : ${binding.switch1.isChecked}")
            Log.e("Result", "Slider State   : ${binding.slider.progress}")
            Log.e("Result", "Country State  : ${binding.autoCompleteTextView.text}")
            if (control){
                val chooseButton = findViewById<Button>(binding.toggleButton.checkedButtonId)
                val buttonText = chooseButton.text.toString()
                Log.e("Result", "Category       : $buttonText")
            }
        }
    }
}