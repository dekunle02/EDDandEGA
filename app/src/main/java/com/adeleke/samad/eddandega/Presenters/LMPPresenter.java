package com.adeleke.samad.eddandega.Presenters;

import android.util.Log;
import android.widget.Toast;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;
import com.adeleke.samad.eddandega.MyDate;
import com.adeleke.samad.eddandega.R;

import java.util.ArrayList;
import java.util.List;

public class LMPPresenter implements ContractInterface.LMPPresenter{

    private ContractInterface.LMPView lmpView;

    public LMPPresenter(ContractInterface.LMPView lmpView) {
        this.lmpView = lmpView;
    }

    @Override
    public List<int[]> calculate(int selectedRadio, MyDate currentDate, MyDate selectedDate) {

        MyDate EDD = new MyDate();
        MyDate LMP = new MyDate();



        if (selectedRadio == ContractClass.LMP_RADIO){
            //User chose to calculate with LMP

            LMP = selectedDate;

            if (LMP.getRawDate()+280 > LMP.getYearLength()){

                int rawDate=(LMP.getRawDate() + 280) - LMP.getYearLength();
                int year=(LMP.getYear()+1);

                EDD = new MyDate(rawDate,year);

            }else if (LMP.getYearLength() >= (LMP.getRawDate()+280)){

                int rawDate = LMP.getRawDate()+280;
                int year = LMP.getYear();
                EDD = new MyDate(rawDate,year);
            }


        }else{
            //User chose to calculate with EDD

            EDD = selectedDate;

            if ( EDD.getRawDate() < 280){

                int rawDate=ContractClass.YEAR_LENGTH - (280 - EDD.getRawDate());
                int year=(currentDate.getYear()-1);
                LMP = new MyDate(rawDate,year);

            }else {
                int rawDate=selectedDate.getRawDate()-280;
                int year=currentDate.getYear();
                LMP = new MyDate(rawDate,year);
            }

        }

        int rawEGA;
        if (currentDate.getYear() == LMP.getYear()) {
            rawEGA = currentDate.getRawDate() - LMP.getRawDate();
        } else {
            rawEGA = (ContractClass.YEAR_LENGTH - LMP.getRawDate()) + currentDate.getRawDate();
        }



        int [] EGAarray = {rawEGA/7, (rawEGA - ((rawEGA/7) * 7)) };
        int [] LMParray = LMP.getNormalDate();
        int [] EDDarray = EDD.getNormalDate();



        List<int [] > result = new ArrayList<int[]>();
        result.add(LMParray);
        result.add(EGAarray);
        result.add(EDDarray);

        return result;
    }
}
