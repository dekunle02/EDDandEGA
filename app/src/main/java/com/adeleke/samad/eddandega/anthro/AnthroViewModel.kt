package com.adeleke.samad.eddandega.anthro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AnthroViewModel : ViewModel() {
    private val TAG = javaClass.simpleName

    var ageBracketInt: Int = 0
        set(value) {
            field = value
            ageBracket = when (value) {
                0 -> AgeBracket.NEONATE
                1 -> AgeBracket.INFANT
                else -> AgeBracket.TODDLER
            }
            calculateWeight()
        }

    var birthWeight: Double = 0.0
        set(value) {
            field = value
            calculateWeight()
        }

    var age: Int = 0
        set(value) {
            field = value
            calculateWeight()
        }

    private var ageBracket = AgeBracket.NEONATE


    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    private fun calculateWeight() {
        var weight: Double = 0.0
        when (ageBracket) {
            AgeBracket.NEONATE -> {
                if (birthWeight == 0.0) {
                    birthWeight = 2.5
                }
                weight = (age - 10) * 30 + birthWeight * 1000
                weight /= 1000
            }
            AgeBracket.INFANT -> {
                weight = ((age + 8) / 2).toDouble()
            }
            AgeBracket.TODDLER -> {
                weight = if (age < 7) ((age * 2 + 8).toDouble()) else ((7 * age - 5) / 2).toDouble()
            }
        }
        _result.value = weight.toString() + "kg"
    }


    enum class AgeBracket { NEONATE, INFANT, TODDLER }
}