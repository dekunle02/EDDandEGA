package com.adeleke.samad.eddandega.fluid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.adeleke.samad.eddandega.Dehydration
import com.adeleke.samad.eddandega.R
import com.adeleke.samad.eddandega.databinding.FragmentFluidBinding

class FluidFragment : Fragment() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: FragmentFluidBinding
    private val viewModel: FluidViewModel by viewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fluid, container, false)

        val dehydrationMap = mapOf<Float, String>(0F to getString(R.string.no_dehydration),
                1F to getString(R.string.mild_dehydration),
                2F to getString(R.string.moderate_dehydration),
                3F to getString(R.string.severe_dehydration)
        )

        // Set up the slider
        binding.dehydrationSlider.valueFrom = 0F
        binding.dehydrationSlider.valueTo = 3F
        binding.dehydrationSlider.setLabelFormatter { value: Float ->
            dehydrationMap[value]!!
        }
        binding.dehydrationSlider.addOnChangeListener { slider, value, fromUser ->
            viewModel.dehydration = when (value) {
                1F -> Dehydration.MILD
                2F -> Dehydration.MODERATE
                3F -> Dehydration.SEVERE
                else -> Dehydration.NONE
            }
        }
        binding.etWeight.setOnKeyListener { _, _, _ ->
            if (!binding.etWeight.text.toString().isNullOrEmpty()){
                viewModel.weight = binding.etWeight.text.toString().toDouble()
            }
            false
        }



        // Observables
        viewModel.maintenance.observe(viewLifecycleOwner, Observer { maintenance ->
            binding.maintenance.text = "${resources.getString(R.string.default_maintenance)}\n$maintenance"
        })
        viewModel.deficit.observe(viewLifecycleOwner, Observer { deficit ->
            binding.deficit.text = "${resources.getString(R.string.default_deficit)}\n$deficit"
        })
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            val total = result?.get("total").toString()
            val q = result?.get("quart").toString()
            binding.tvResult.text = "You should give " + total + "mls over 24hrs \n" +
                    "About " + q + "mls every 8hrs"
        })

        return binding.root
    }


}