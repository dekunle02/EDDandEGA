package com.adeleke.samad.eddandega.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;
import com.adeleke.samad.eddandega.MyDate;
import com.adeleke.samad.eddandega.Presenters.LMPPresenter;
import com.adeleke.samad.eddandega.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class LMPFragment extends Fragment implements ContractInterface.LMPView {

    ContractInterface.LMPPresenter lmpPresenter;
    ProgressBar progressBar;
    TextView tv_EDD, tv_EGA, tv_LMP;
    RadioGroup radioGroup;
    DatePicker datePicker;
    ConstraintLayout mainConstraint;
    Button calculateButton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lmp, container, false);

        lmpPresenter = new LMPPresenter(this);
        progressBar = view.findViewById(R.id.progressBar);
        tv_EDD = view.findViewById(R.id.tv_EDD);
        tv_EGA = view.findViewById(R.id.tv_EGA);
        tv_LMP = view.findViewById(R.id.tv_LMP);
        radioGroup = view.findViewById(R.id.radioGroup);
        datePicker=view.findViewById(R.id.datePicker);
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

                new CountDownTimer(1000, 1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        List<int[]> result = lmpPresenter.calculate(radioGroup.getCheckedRadioButtonId(),  ContractClass.getTodaysDate(), fetchSelectedDate(datePicker));
                        attempt(result.get(0), result.get(1), result.get(2));

                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }.start();

            }
        });



        return view;
    }

    private void clearTv(){
        tv_EGA.setText("");
        tv_EDD.setText("");
        tv_LMP.setText("");
    }



    @Override
    public MyDate fetchSelectedDate(DatePicker datePicker) {
        return new MyDate(new int[]{datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear()});
    }

    @Override
    public void error() {
        String  error_message = getResources().getString(R.string.error_message);
        Log.i("tag", error_message);
    }

    @Override
    public void attempt(int[] LMP, int[] EGA, int[] EDD) {
        if( EGA[0]>45 || EGA[0]<=0 || LMP[2]>= (ContractClass.getTodaysDate().getYear() + 2)){
            error();
        }

        upDateDisplay(LMP, EGA, EDD);
    }

    @Override
    public void upDateDisplay(int[] LMP, int[] EGA, int[] EDD) {
        String lmpBase = getResources().getString(R.string.lmp);
        String lmpDay = Integer.toString(LMP[0]);
        String lmpMonth = getResources().getStringArray(R.array.monthNames)[LMP[1]];
        String lmpYear = Integer.toString(LMP[2]);
        tv_LMP.setText(lmpBase+ " " + lmpDay + " "+ lmpMonth+ ", " + lmpYear + ". ");

        String egaBase = getResources().getString(R.string.ega);
        String egaWeek = Integer.toString(EGA[0]);
        String egaDay = Integer.toString(EGA[1]);
        String egaAppell = " days.";
        if (EGA[1] == 1){
            egaAppell = " day.";
        }else if (EGA[1] == 0){
            egaDay = "";
            egaAppell = ".";
        }

        tv_EGA.setText(egaBase + " "+ egaWeek + " weeks, "+ egaDay+ egaAppell);


        String eddBase = getResources().getString(R.string.edd);
        String eddDay = Integer.toString(EDD[0]);
        String eddMonth = getResources().getStringArray(R.array.monthNames)[EDD[1]];
        String eddYear = Integer.toString(EDD[2]);
        tv_EDD.setText(eddBase + " "+ eddDay + " "+ eddMonth+ ", " + eddYear + ". ");
    }
}
