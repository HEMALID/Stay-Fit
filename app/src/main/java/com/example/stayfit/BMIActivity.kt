package com.example.stayfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.stayfit.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW="US_UNITS_VIEW"
    }

    private var currentVisibleView: String=METRIC_UNITS_VIEW

    private lateinit var binding: ActivityBmiactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title="CALCULATOR BMI"
        }

        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsMetricUnitsView()
            }

        }

        binding.btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }

    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView=METRIC_UNITS_VIEW
        binding.tilMetricUnitHeight.visibility=View.VISIBLE
        binding.tilMetricUnitWeight.visibility=View.VISIBLE

        binding.tilUsMetricUnitWeight.visibility=View.GONE
        binding.tilMetricUsUnitHeightFeet.visibility=View.GONE
        binding.tilMetricUsUnitHeightInch.visibility=View.GONE

        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()


        binding.llDiplayBMIResult.visibility=View.INVISIBLE
    }

    private fun makeVisibleUsMetricUnitsView() {
        currentVisibleView=US_UNITS_VIEW
        binding.tilMetricUnitHeight.visibility=View.INVISIBLE
        binding.tilMetricUnitWeight.visibility=View.INVISIBLE

        binding.tilUsMetricUnitWeight.visibility=View.VISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility=View.VISIBLE
        binding.tilMetricUsUnitHeightInch.visibility=View.VISIBLE

        binding.etUsMetricUnitHeightFeet.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()


        binding.llDiplayBMIResult.visibility=View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel="Very severely under Weight"
            bmiDescription="Oops! You really need to tack better care of your self! Eat more! "
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel="Severely under Weight"
            bmiDescription="Oops! You really need to tack better care of your self! Eat more! "
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel="Under Weight"
            bmiDescription="Oops! You really need to tack better care of your self! Eat more! "
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel="Normal"
            bmiDescription="Congratulations! You are in good shape! "
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel="Over Weight"
            bmiDescription=" Oops! You really need to tack better care of your self! Workout "
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel="Obese class | (Moderately obese)"
            bmiDescription=" Oops! You really need to tack better care of your self! Workout "
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel="Obese class || (Severely obese)"
            bmiDescription=" OMG! You are  in a vary dangers condition! Act Now "
        } else {
            bmiLabel="Obese class ||| (Very Severely obese)"
            bmiDescription=" OMG! You are  in a vary dangers condition! Act Now"
        }
        var bmiValue=BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDiplayBMIResult.visibility=View.VISIBLE
        binding.tvBMIValue.text=bmiValue

        binding.tvBMIType.text=bmiLabel
        binding.tvBMIDescription.text=bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid=true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid=false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid=false
        }
        return isValid
    }

    private fun validateUsUnits(): Boolean {

        var isValid=true

        when {
            binding.etUsMetricUnitWeight.text.toString().isEmpty() -> {
                isValid=false
            }
            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty() -> {
                isValid=false
            }
            binding.etUsMetricUnitHeightInch.text.toString().isEmpty() -> {
                isValid=false
            }
        }
        return isValid
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue: Float=binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue: Float=binding.etMetricUnitWeight.text.toString().toFloat()

                val bmi=weightValue / (heightValue * heightValue)
                displayBMIResult(bmi)

            } else {
                Toast.makeText(this, "Please enter valid value", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (validateUsUnits()) {
                val usUniteHeightValueFeet: String=binding.etUsMetricUnitHeightFeet.text.toString()
                val usUniteHeightValueInch: String=binding.etUsMetricUnitHeightInch.text.toString()

                val usUniteWeightValue: Float=binding.etUsMetricUnitWeight.text.toString().toFloat()

                val heightValue=
                    usUniteHeightValueInch.toFloat() + usUniteHeightValueFeet.toFloat() * 12
                val bmi=703 * (usUniteWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)
            } else {
                Toast.makeText(this, "Please enter valid value", Toast.LENGTH_SHORT).show()
            }
        }
    }
}