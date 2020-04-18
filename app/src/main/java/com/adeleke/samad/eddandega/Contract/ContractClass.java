package com.adeleke.samad.eddandega.Contract;

import com.adeleke.samad.eddandega.MyDate;
import com.adeleke.samad.eddandega.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContractClass {

    public final static int LMP_RADIO = R.id.radio_lmp;
    public final static int EDD_RADIO = R.id.radio_edd;
    public final static int YEAR_LENGTH = 365;
    public final static int LEAP_YEAR_LENGTH = 366;

    public static MyDate getTodaysDate() {
        //String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
        //String[] dateArray = currentDate.split("-");
//        int year = Integer.parseInt(dateArray[0]);
//        int month = Integer.parseInt(dateArray[1]);
//        int day = Integer.parseInt(dateArray[2]);

        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH);
        int year = rightNow.get(Calendar.YEAR);

        return new MyDate(new int[]{day, month, year});
    }

    public final static int[] nonLeapMonthLengths = {31, 28, 31,30,31,30,31,31,30,31,30,31};

    public final static int[] leapMonthLengths = {31,29,31,30,31,30,31,31,30,31,30,31};



    public static final int MILD_DEHYDRATION = 50;
    public static final int MODERATE_DEHYDRATION =75;
    public static final int SEVERE_DEHYDRATION = 100;
    public static final String NOT_DEHYDRATED_STRING = "Not dehydrated";
    public static final String MILD_DEHYDRATION_STRING = "Mild dehydration";
    public static final String MODERATE_DEHYDRATION_STRING = "Moderate dehydration";
    public static final String SEVERE_DEHYDRATION_STRING = "Severe dehydration";
    public static String[] SPINNER_ITEMS = {NOT_DEHYDRATED_STRING, MILD_DEHYDRATION_STRING, MODERATE_DEHYDRATION_STRING, SEVERE_DEHYDRATION_STRING};


    public static final String NEONATE_BRACKET = "0-3 months";
    public static final String INFANT_BRACKET = "4-12 months";
    public static final String CHILD_BRACKET = "> 1 yr";
    public static final String[] ANTHROPOMETRY_SPINNER_ITEMS = {NEONATE_BRACKET,INFANT_BRACKET,CHILD_BRACKET};
    public static final String NEONATE_FORMULA = "";
    public static final String INFANT_FORMULA = "";
    public static final String CHILD_FORMULA = "";




}
