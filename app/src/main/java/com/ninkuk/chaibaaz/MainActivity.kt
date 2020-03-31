package com.ninkuk.chaibaaz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.devs.vectorchildfinder.VectorChildFinder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.result_view.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedCup: Int = 0
        var selectedMilkType: Int = 0

        chai_cup_cutting_container.setOnClickListener {
            changeCupColor(true, chai_cup_cutting)
            changeCupColor(false, chai_cup_full)
            selectedCup = 1
        }

        chai_cup_full_container.setOnClickListener {
            changeCupColor(true, chai_cup_full)
            changeCupColor(false, chai_cup_cutting)
            selectedCup = 2
        }

        two_fat_button.setOnClickListener {
            two_fat_button.backgroundTintList = resources.getColorStateList(R.color.pale)
            full_fat_button.backgroundTintList = resources.getColorStateList(R.color.mudBrown)
            two_fat_button.setTextColor(getColor(R.color.mudBrown))
            full_fat_button.setTextColor(getColor(R.color.pale))
            selectedMilkType = 2
        }

        full_fat_button.setOnClickListener {
            full_fat_button.backgroundTintList = resources.getColorStateList(R.color.pale)
            two_fat_button.backgroundTintList = resources.getColorStateList(R.color.mudBrown)
            full_fat_button.setTextColor(getColor(R.color.mudBrown))
            two_fat_button.setTextColor(getColor(R.color.pale))
            selectedMilkType = 1
        }

        generateRecipeButton.setOnClickListener {

            val n = numberPicker.progress
            var waterBase = 0.0

            var chaiPattiMeasurement = (n + 1).toDouble()
            var sugarMeasurement = (n + 1).toDouble()

            when (selectedCup) {
                0 -> {
                    Toast.makeText(this, "Please select the serving cup size!", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
                1 -> {
                    waterBase = 8.0
                    chaiPattiMeasurement /= 2
                    sugarMeasurement /= 2
                }
                2 -> {
                    waterBase = 4.0
                }
            }

            val waterMeasurement = n / waterBase
            val milkMeasurement = waterMeasurement * 2


            if (selectedMilkType == 0) {
                Toast.makeText(this, "Please select the milk type!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            MaterialDialog(this).show {
                customView(R.layout.result_view, scrollable = false)
                milk_measurement.text = milkMeasurement.toString()
                water_measurement.text = waterMeasurement.toString()
                patti_measurement.text = chaiPattiMeasurement.toString()
                sugar_measurement.text = sugarMeasurement.toString()
            }
        }
    }

    private fun changeCupColor(selected: Boolean, selectedCup: ImageView) {
        val cupVector = VectorChildFinder(this, R.drawable.ic_cutting_chai_cup, selectedCup)
        val cupBG = cupVector.findPathByName("cupBG")

        val bgFillColor = if (selected) {
            getColor(R.color.pale)
        } else {
            getColor(R.color.mudBrown)
        }

        cupBG.fillColor = bgFillColor
        selectedCup.invalidate()
    }
}
