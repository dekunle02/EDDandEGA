package com.adeleke.samad.eddandega.lmp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LMPViewModel : ViewModel() {
    private val TAG = javaClass.simpleName

    private val lmpHelper = LmpHelper()

    private var mode = Mode.LMP


    private lateinit var datePicked: Map<String, Int>
    private var daysPicked: Int = 0
    private var weeksPicked: Int = 0


    // Observables
    private val _result = MutableLiveData<String?>()
    val result: LiveData<String?>
        get() = _result

    private val _canShowEga = MutableLiveData<Boolean>()
    val canShowEga: LiveData<Boolean>
        get() = _canShowEga


    fun useLmp() {
        mode = Mode.LMP
        _canShowEga.value = false
        updateText()
    }

    fun useEga() {
        mode = Mode.EGA
        _canShowEga.value = true
        updateText()
    }

    fun useEdd() {
        mode = Mode.EDD
        _canShowEga.value = false
        updateText()
    }

    fun setDatePicked(dateMap: Map<String, Int>) {
        Log.d(TAG, "setDatePicked: $dateMap")
        datePicked = dateMap
        updateText()
    }

    fun setEga(num: Float) {
        mode = Mode.EGA
        weeksPicked = num.toInt() / 7
        daysPicked = num.toInt() % 7
        updateText()
    }

    private fun updateText() {
        val day = datePicked["day"]!!
        val month = datePicked["month"]!!
        val year = datePicked["year"]!!

        when (mode) {
            Mode.LMP -> lmpHelper.setUpWithLMP(day = day, month = month, year = year)
            Mode.EDD -> lmpHelper.setUpWithEDD(day = day, month = month, year = year)
            Mode.EGA -> lmpHelper.setUpWithEGAatDate(
                    days = daysPicked.toLong(),
                    weeks = weeksPicked.toLong(),
                    day = day,
                    month = month,
                    year = year
            )
        }

        _result.value = lmpHelper.toString()

    }


    enum class Mode { LMP, EDD, EGA }

}


