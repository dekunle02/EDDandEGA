package com.adeleke.samad.eddandega.lmp


import java.text.DateFormat
import java.util.*


class LmpHelper() {
    private val TAG = javaClass.simpleName
    //  var dateFormat = SimpleDateFormat("EEEE dd MMM yyyy", Locale.ENGLISH)
   private val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
//    private val dateFormat: java.text.DateFormat = SimpleDateFormat.getDateInstance()
    private lateinit var lmp: Calendar
    private lateinit var ega: Map<String, Long>
    private lateinit var edd: Calendar


    fun setUpWithLMP(day: Int, month: Int, year: Int) {
        lmp = makeCalFromInt(day, month, year)
        edd = getEDDWithLMP(lmp)
        ega = getEGAWithLMP(lmp)
    }

    fun setUpWithEDD(day: Int, month: Int, year: Int) {
        edd = makeCalFromInt(day, month, year)
        lmp = getLMPWithEDD(edd)
        ega = getEGAWithLMP(lmp)
    }

    fun setUpWithEGAatDate(weeks: Long, days: Long, day: Int, month: Int, year: Int) {
        val date = makeCalFromInt(day, month, year)
        val oldEGA = mapOf("weeks" to weeks, "days" to days)

        lmp = getLMPUsingEGAatDATE(oldEGA, date)
        edd = getEDDWithLMP(lmp)
        ega = getEGAWithLMP(lmp)

    }


    override fun toString(): String {
        val lmpString = "LMP is ${dateFormat.format(lmp.time)}"
        val eddString = "EDD is ${dateFormat.format(edd.time)}"
        val egaString = "EGA is ${ega["weeks"]} weeks ${ega["days"]} days"
        return lmpString + "\n" + egaString + "\n" + eddString
    }

    private fun getEDDWithLMP(lmp: Calendar): Calendar {
        val edd = Calendar.getInstance()
        edd.timeInMillis = lmp.timeInMillis
        edd.add(Calendar.DAY_OF_YEAR, 280)
        return edd
    }

    private fun getEGAWithLMP(lmp: Calendar): Map<String, Long> {
        val today = Calendar.getInstance()
        augmentDate(today)
        val diff = today.timeInMillis - lmp.timeInMillis
        val allDays = diff / (24 * 60 * 60 * 1000)
        val weeks = allDays / 7
        val remainingDays = allDays % 7

        return mapOf("weeks" to weeks, "days" to remainingDays)
    }

    private fun getLMPWithEDD(edd: Calendar): Calendar {
        val lmp = Calendar.getInstance()
        lmp.timeInMillis = edd.timeInMillis
        lmp.add(Calendar.DAY_OF_YEAR, -280)
        return lmp
    }

    private fun getLMPUsingEGAatDATE(ega: Map<String, Long>, date: Calendar): Calendar {
        val weeks = ega["weeks"]
        var days = ega["days"]
        days = days!! + (weeks!! * 7)

        val lmp = Calendar.getInstance()
        augmentDate(date)
        augmentDate(lmp)
        lmp.timeInMillis = date.timeInMillis
        lmp.add(Calendar.DAY_OF_YEAR, -days.toInt())
        return lmp
    }

    private fun makeCalFromInt(day: Int, month: Int, year: Int): Calendar {
        val date = GregorianCalendar(year, month, day).time
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun augmentDate(c: Calendar) {
        c.set(Calendar.MILLISECOND, 0)
    }

}