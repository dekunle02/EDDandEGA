package com.adeleke.samad.eddandega.Fragments;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;
import com.adeleke.samad.eddandega.Presenters.FluidPresenter;
import com.adeleke.samad.eddandega.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;


public class FluidFragment extends Fragment implements ContractInterface.FluidView {

    private TextView tv_maintenance, tv_deficit, tv_info;
    private EditText et_weight;
    private Spinner spinner_dehydration_level;
    private ProgressBar progressBar;
    private Button button_fluid;

    double weight;
    double deficit;

    ContractInterface.FluidPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fluid, container, false);
        tv_maintenance = view.findViewById(R.id.tv_maintenance);
        tv_deficit = view.findViewById(R.id.tv_deficit);
        tv_info = view.findViewById(R.id.tv_info);
        et_weight = view.findViewById(R.id.et_weight);
        spinner_dehydration_level = view.findViewById(R.id.spinner_dehydration_level);
        progressBar = view.findViewById(R.id.progressBar);
        button_fluid = view.findViewById(R.id.button_fluid);

        presenter = new FluidPresenter(this);



        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ContractClass.SPINNER_ITEMS);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner_dehydration_level.setAdapter(spinnerAdapter);


        defaultState();

        button_fluid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (et_weight.getText().toString().trim().length() == 0 || Double.parseDouble(et_weight.getText().toString()) == 0){
                    onError();
                }else if(Double.parseDouble(et_weight.getText().toString()) > 59){
                   Toast.makeText(getContext(), getResources().getString(R.string.adult_weight_message), Toast.LENGTH_SHORT).show();
                    tv_maintenance.setText("3000mls");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);

                    weight = Double.parseDouble(et_weight.getText().toString());

                    if (spinner_dehydration_level.getSelectedItem() == ContractClass.MILD_DEHYDRATION_STRING){
                        deficit = ContractClass.MILD_DEHYDRATION;
                    }else if(spinner_dehydration_level.getSelectedItem() == ContractClass.MODERATE_DEHYDRATION_STRING){
                        deficit = ContractClass.MODERATE_DEHYDRATION;
                    }else if (spinner_dehydration_level.getSelectedItem() == ContractClass.SEVERE_DEHYDRATION_STRING){
                        deficit = ContractClass.SEVERE_DEHYDRATION;
                    }else{
                        deficit=0;
                    }


                    new CountDownTimer(1000, 1000){

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            onSuccess(presenter.calculateMaintenance(weight), presenter.calculateDeficit(weight, deficit));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }.start();

                }

            }
        });

        return view;
}

    @Override
    public void onSuccess(double maintenance, double deficit) {

        DecimalFormat df = new DecimalFormat("##");
        String maintenanceString = df.format(maintenance) + "mls";
        String deficitString = df.format(deficit) + "mls";
        double totalFluid = maintenance+deficit;
        String info;
        info = "You should give " + df.format(totalFluid) + "mls over 24hrs divided into " + df.format(totalFluid/3)+ "mls every 8hrs";


        tv_maintenance.setText(maintenanceString);
        tv_deficit.setText(deficitString);
        tv_info.setText(info);
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), getResources().getString(R.string.error_message_fluid), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void inProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void defaultState() {
        progressBar.setVisibility(View.INVISIBLE);
        weight=0;
        deficit=0;
        tv_info.setText(getResources().getString(R.string.default_info));
    }
}
