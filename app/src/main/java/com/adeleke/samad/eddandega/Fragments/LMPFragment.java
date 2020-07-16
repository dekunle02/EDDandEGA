package com.adeleke.samad.eddandega.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;
import com.adeleke.samad.eddandega.Presenters.LMPPresenter;
import com.adeleke.samad.eddandega.R;

import java.time.LocalDate;


public class LMPFragment extends Fragment implements ContractInterface.LMPView {

    ContractInterface.LMPPresenter lmpPresenter;
    ProgressBar progressBar;
    TextView tv_result;
    RadioGroup radioGroup;
    DatePicker datePicker;
    ConstraintLayout mainConstraint;
    Button calculateButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lmp, container, false);

        lmpPresenter = new LMPPresenter(this);
        progressBar = view.findViewById(R.id.progressBar);
        tv_result = view.findViewById(R.id.tv_result);
        radioGroup = view.findViewById(R.id.radioGroup);
        datePicker = view.findViewById(R.id.datePicker);
        mainConstraint = view.findViewById(R.id.mainConstraint);
        calculateButton = view.findViewById(R.id.button_calculate);


        radioGroup.check(R.id.radio_lmp);
        progressBar.setVisibility(View.INVISIBLE);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                clearTv();
            }
        });


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        LocalDate selectedDate = fetchSelectedDate(datePicker);
                        ContractClass.DateType selectedRadio = fetchSelectedRadio(radioGroup);
                        String[] result = lmpPresenter.calculate(selectedRadio, selectedDate);
                        displayDates(result);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }.start();

            }
        });


        return view;
    }

    private void clearTv() {
        tv_result.setText("");
    }

    @Override
    public LocalDate fetchSelectedDate(DatePicker datePicker) {
        String day = Integer.toString(datePicker.getDayOfMonth());
        String month = Integer.toString(datePicker.getMonth() + 1);
        String year = Integer.toString(datePicker.getYear());
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        String date = (year + '-' + month + '-' + day);
        return LocalDate.parse(date);
    }

    @Override
    public ContractClass.DateType fetchSelectedRadio(RadioGroup radioGroup) {
        int checkedButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedButtonId == R.id.radio_edd) {
            return ContractClass.DateType.EDD;
        } else {
            return ContractClass.DateType.LMP;
        }
    }

    @Override
    public void displayDates(String[] results) {
        String result = TextUtils.join("\n", results);
        tv_result.setText(result);
    }

}
