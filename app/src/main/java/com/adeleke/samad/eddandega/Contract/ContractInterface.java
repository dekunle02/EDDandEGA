package com.adeleke.samad.eddandega.Contract;

import android.widget.DatePicker;
import android.widget.RadioGroup;

import java.time.LocalDate;

public interface ContractInterface {


    interface LMPView {

//         MyDate fetchSelectedDate (DatePicker datePicker);
//         void error();
//         void attempt(int[] LMP, int[] EGA, int[] EDD);
//         void upDateDisplay(int[] LMP, int[] EGA, int[] EDD);

            LocalDate fetchSelectedDate (DatePicker datePicker);
            ContractClass.DateType fetchSelectedRadio (RadioGroup radioGroup);
            void displayDates (String[] results);

    }


    interface LMPPresenter {

//        List<int[]> calculate (int selectedRadio, MyDate currentDay, MyDate selectedDate);
        String[] calculate(ContractClass.DateType dateType, LocalDate datePicked);

    }


    public interface FluidView {
        void onSuccess(double maintenance, double deficit);

        void onError();

        void inProgress();

        void defaultState();
    }

    public interface FluidPresenter {
        double calculateMaintenance(double weight);

        double calculateDeficit(double weight, double deficit);
    }


    public interface AnthropometryView {
        void switchDisplaysbyBracket(RadioGroup radioGroup);

        boolean checkValidEntry();

        void displayAnswer(double weight);
    }

    public interface AnthropometryPresenter {
        double calculateWeight(double age, String ageBracket, double birthWeight);
    }


}
