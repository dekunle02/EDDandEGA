package com.adeleke.samad.eddandega.anthro

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.adeleke.samad.eddandega.R
import com.adeleke.samad.eddandega.databinding.FragmentAnthropometryBinding

class AnthroFragment : Fragment() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: FragmentAnthropometryBinding
    private val viewModel: AnthroViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anthropometry, container, false)

        binding.ageEditText.requestFocus()

        binding.ageSpinner.setItems("day(s)", "month(s)", "year(s)")
        binding.ageSpinner.selectedIndex = 0
        binding.ageSpinner.setOnItemSelectedListener { view, position, id, item ->
            viewModel.ageBracketInt = position
            binding.birthWeightTT.visibility = if (position == 0) View.VISIBLE else View.GONE
        }

        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == (Configuration.UI_MODE_NIGHT_YES)) {
            binding.ageSpinner.setBackgroundColor(resources.getColor(R.color.textColorPrimary))
        }


        binding.ageEditText.setOnKeyListener { view, i, keyEvent ->
            if (!binding.ageEditText.text.toString().isNullOrEmpty()){
                viewModel.age = binding.ageEditText.text.toString().toInt()
            }
            false
        }
        binding.birthWeightEditText.setOnKeyListener { view, i, keyEvent ->
            if (!binding.birthWeightEditText.text.toString().isNullOrEmpty()){
                viewModel.birthWeight = binding.birthWeightEditText.text.toString().toDouble()
            }
            false
        }

        // Observables
        viewModel.result.observe(viewLifecycleOwner, Observer { r ->
            binding.resultTextView.text = getString(R.string.appr_weight) + r
        })

        return binding.root
    }
}