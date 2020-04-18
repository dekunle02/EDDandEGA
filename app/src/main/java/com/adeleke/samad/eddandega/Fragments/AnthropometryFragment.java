package com.adeleke.samad.eddandega.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;
import com.adeleke.samad.eddandega.Presenters.AnthropometryPresenter;
import com.adeleke.samad.eddandega.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;


public class AnthropometryFragment extends Fragment implements ContractInterface.AnthropometryView {

    private AnthropometryPresenter presenter;
    private RadioGroup mRadioGroup;
    private TextView tv_anthro_info, tv_age;
    private EditText et_birth_weight, et_age;

    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anthropometry, container, false);;

        mRadioGroup = view.findViewById(R.id.radioGroup_anthro);
        tv_anthro_info = view.findViewById(R.id.tv_anthro_info);
        tv_age = view.findViewById(R.id.tv_anthropometry_age);
        et_birth_weight = view.findViewById(R.id.et_anthro_birthWeight);
        et_age = view.findViewById(R.id.et_anthro_age);

        presenter = new AnthropometryPresenter(this);
        progressBar = view.findViewById(R.id.progressBar_anthro);

        mRadioGroup.check(R.id.radio_neonate);
        switchDisplaysbyBracket(mRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switchDisplaysbyBracket(radioGroup);
            }
        });


        progressBar.setVisibility(View.INVISIBLE);

        Button button = view.findViewById(R.id.button_anthro);;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidEntry()){
                    progressBar.setVisibility(View.VISIBLE);
                    new CountDownTimer(1000, 1000){

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            progressBar.setVisibility(View.INVISIBLE);
                            String ageBracket;
                            if (mRadioGroup.getCheckedRadioButtonId() == R.id.radio_child ){ageBracket = ContractClass.CHILD_BRACKET;}
                            else if (mRadioGroup.getCheckedRadioButtonId() == R.id.radio_neonate ){ageBracket = ContractClass.NEONATE_BRACKET;}
                            else{ageBracket=ContractClass.INFANT_BRACKET;}

                            Double weight = presenter.calculateWeight(Double.parseDouble(et_age.getText().toString()),ageBracket,Double.parseDouble(et_birth_weight.getText().toString()));
                            displayAnswer(weight);

                        }
                    }.start();


                }else{
                    Toast.makeText(getContext(), getResources().getString(R.string.error_anthro), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    @Override
    public void switchDisplaysbyBracket(RadioGroup radioGroup) {
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radio_neonate:
                tv_age.setText(" age(days) ");
                et_birth_weight.setEnabled(true);;
                break;
            case R.id.radio_infant:
                tv_age.setText ( "age(months)");
                et_birth_weight.setText("0");
                et_birth_weight.setEnabled(false);
                break;
            case R.id.radio_child:
                tv_age.setText ( "age(years)");
                et_birth_weight.setText("0");
                et_birth_weight.setEnabled(false);
                break;
        }
    }

    @Override
    public boolean checkValidEntry() {

        if (et_age.getText().toString() == null || et_age.getText().toString().trim().length() == 0){
           return false;
        }
        return true;
    }

    @Override
    public void displayAnswer(double weight) {

        DecimalFormat df = new DecimalFormat("##.#");
        String weightString = df.format(weight) + " kg.";

        tv_anthro_info.setText("Approximate Weight is " + weightString );
    }
}
