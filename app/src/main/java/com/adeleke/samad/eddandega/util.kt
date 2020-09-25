package com.adeleke.samad.eddandega

const val MILD_DEHYDRATION = 50
const val MODERATE_DEHYDRATION = 75
const val SEVERE_DEHYDRATION = 100

enum class Dehydration { NONE, MILD, MODERATE, SEVERE }


const val NEONATE_BRACKET = "0-3 months"
const val INFANT_BRACKET = "4-12 months"
const val CHILD_BRACKET = "> 1 yr"


val ANTHROPOMETRY_SPINNER_ITEMS = arrayOf(NEONATE_BRACKET, INFANT_BRACKET, CHILD_BRACKET)
const val NEONATE_FORMULA = ""
const val INFANT_FORMULA = ""
const val CHILD_FORMULA = ""