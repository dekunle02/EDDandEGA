package com.adeleke.samad.eddandega;

import com.adeleke.samad.eddandega.Contract.ContractClass;

public class MyDate {
    private int day;
    private int month;
    private int year;
    private int[] normalDate;
    private int [] monthLengths = ContractClass.nonLeapMonthLengths;
    private int yearLength = ContractClass.YEAR_LENGTH;
    private int rawDate;

    public MyDate() {
    }

    public MyDate(int[] normalDate) {
        this.normalDate = normalDate;
        this.day = normalDate[0];
        this.month = normalDate[1];
        this.year = normalDate[2];

        this.rawDate = fetchRawDate(day, month, year,monthLengths);

    }

    public MyDate(int rawDate, int year){
        this.rawDate = rawDate;
        this.year = year;

        this.normalDate = fetchNormalDate(rawDate, year);
    }

    private boolean isLeap(int year) {
        return year % 4 == 0;
    }



    private int fetchRawDate (int day, int month, int year, int[] monthLengths) {
        int rawDate=0;
        if (isLeap(year)){
            monthLengths = ContractClass.leapMonthLengths;
        }
        for (int x = 0; x < month; x++) {
            rawDate = rawDate + monthLengths[x];
        }
        rawDate = rawDate + day;
        return rawDate;
    }

    private int[] fetchNormalDate (int rawDate, int year) {

       int month = 0;
       int day = 0;

       if (isLeap(year)) {
           monthLengths = ContractClass.leapMonthLengths;
        }

        for (int x = 0; x < 12; x++) {
            if (rawDate > monthLengths[x]) {
                month++;
                rawDate = rawDate - monthLengths[x];
            } else {
                day = rawDate;
            }
        }

        int [] normalDate = {day, month, year};

        return normalDate;

    }



    public int[] getNormalDate(){

        return normalDate;
    }

    public int getRawDate() {

        return rawDate;
    }



    public int getDay(){
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getYearLength(){
        if (isLeap(year)){
            yearLength = ContractClass.LEAP_YEAR_LENGTH;
        }
        return yearLength;
    }



    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRawDate(int rawDate) {
        this.rawDate = rawDate;
    }
}
