package com.adeleke.samad.eddandega.Contract;

public class ContractClass {

    public enum DateType {
        LMP, EDD
    }


    public static final int MILD_DEHYDRATION = 50;
    public static final int MODERATE_DEHYDRATION = 75;
    public static final int SEVERE_DEHYDRATION = 100;
    public static final String NOT_DEHYDRATED_STRING = "Not dehydrated";
    public static final String MILD_DEHYDRATION_STRING = "Mild dehydration";
    public static final String MODERATE_DEHYDRATION_STRING = "Moderate dehydration";
    public static final String SEVERE_DEHYDRATION_STRING = "Severe dehydration";
    public static String[] SPINNER_ITEMS = {NOT_DEHYDRATED_STRING, MILD_DEHYDRATION_STRING, MODERATE_DEHYDRATION_STRING, SEVERE_DEHYDRATION_STRING};


    public static final String NEONATE_BRACKET = "0-3 months";
    public static final String INFANT_BRACKET = "4-12 months";
    public static final String CHILD_BRACKET = "> 1 yr";
    public static final String[] ANTHROPOMETRY_SPINNER_ITEMS = {NEONATE_BRACKET, INFANT_BRACKET, CHILD_BRACKET};
    public static final String NEONATE_FORMULA = "";
    public static final String INFANT_FORMULA = "";
    public static final String CHILD_FORMULA = "";

}
