package com.adeleke.samad.eddandega.lmp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.adeleke.samad.eddandega.R
import com.adeleke.samad.eddandega.databinding.FragmentLmpBinding


class LMPFragment : Fragment() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: FragmentLmpBinding
    private val viewModel: LMPViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lmp, container, false)
        binding.viewModel = viewModel


        binding.egaSlider.valueFrom = 0F
        binding.egaSlider.valueTo = 280F
        binding.egaSlider.setLabelFormatter { value: Float ->
            numToWk(value)
        }
        binding.egaSlider.addOnChangeListener { slider, value, fromUser ->
            viewModel.setEga(value)
        }

        // default values
        readDefaultValue()

        // Click listeners
        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                R.id.lmpButton -> viewModel.useLmp()
                R.id.eddButton -> viewModel.useEdd()
                R.id.egaButton -> viewModel.useEga()
            }
        }

        binding.datePicker.setOnDateChangedListener { _, year, month, day ->
            viewModel.setDatePicked(mapOf("day" to day, "month" to month, "year" to year))
        }

//         Observables
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            binding.tvResult.text = result!!
        })
        viewModel.canShowEga.observe(viewLifecycleOwner, Observer { canShowEga ->
            binding.egaSliderLayout.visibility = if(canShowEga) View.VISIBLE else View.INVISIBLE
        })


        return binding.root
    }


    private fun readDefaultValue() {
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year
        viewModel.setDatePicked(mapOf("day" to day, "month" to month, "year" to year))
        viewModel.useLmp()
    }

    private fun numToWk(num: Float): String {
        val week = num.toInt() / 7
        val day = num.toInt() % 7
        return "$week wks $day days"
    }

}