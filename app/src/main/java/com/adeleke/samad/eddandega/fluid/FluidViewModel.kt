package com.adeleke.samad.eddandega.fluid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adeleke.samad.eddandega.Dehydration
import com.adeleke.samad.eddandega.MILD_DEHYDRATION
import com.adeleke.samad.eddandega.MODERATE_DEHYDRATION
import com.adeleke.samad.eddandega.SEVERE_DEHYDRATION

class FluidViewModel : ViewModel() {
    private val TAG = javaClass.simpleName


    var dehydration = Dehydration.NONE
        set(value) {
            field = value
            calculateDeficit()
            calculateMaintenance()
            calculateTotal()
        }


    var weight = 0.0
        set(value) {
            field = value
            calculateDeficit()
            calculateMaintenance()
            calculateTotal()
        }


    var maintenanceValue: Double = 0.0
    var deficitValue: Double = 0.0

    // Observables
//    val weightString = MutableLiveData<String>()

    private val _maintenance = MutableLiveData<String?>()
    val maintenance: LiveData<String?>
        get() = _maintenance

    private val _deficit = MutableLiveData<String?>()
    val deficit: LiveData<String?>
        get() = _deficit

    private val _result = MutableLiveData<Map<String, Int>>()
    val result: LiveData<Map<String, Int>?>
        get() = _result


    private fun calculateMaintenance() {
        val maintenance: Double = if (weight <= 10) {
            weight * 100
        } else if (weight > 10 && weight <= 20) {
            1000 + (weight - 10) * 50
        } else {
            1500 + (weight - 20) * 20
        }
        maintenanceValue = maintenance
        _maintenance.value = maintenance.toInt().toString() + " mls"
    }

    private fun calculateDeficit() {
        val deficit: Double = if (dehydration == Dehydration.MILD) {
            weight * MILD_DEHYDRATION
        } else if (dehydration == Dehydration.MODERATE) {
            weight * MODERATE_DEHYDRATION
        } else if (dehydration == Dehydration.SEVERE) {
            weight * SEVERE_DEHYDRATION
        } else {
            0.0
        }
        deficitValue = deficit
        _deficit.value = deficit.toInt().toString() + " mls"
    }

    private fun calculateTotal() {
        val total = (maintenanceValue + deficitValue).toInt()
        val q = total / 3
        _result.value = mapOf("total" to total, "quart" to q)
    }

}