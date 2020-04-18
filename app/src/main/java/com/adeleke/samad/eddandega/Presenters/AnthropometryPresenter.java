package com.adeleke.samad.eddandega.Presenters;

import android.widget.RadioGroup;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;

public class AnthropometryPresenter implements ContractInterface.AnthropometryPresenter {

    ContractInterface.AnthropometryView view;

    public AnthropometryPresenter(ContractInterface.AnthropometryView view) {
        this.view = view;
    }

    @Override
    public double calculateWeight(double age, String ageBracket, double birthWeight) {
        double weight = 0;

        switch (ageBracket){
            case ContractClass.NEONATE_BRACKET:
                if (birthWeight==0){
                    birthWeight=2500;
                }
                weight = ((age - 10) * 30) + (birthWeight * 1000);
                weight = weight / 1000;
                break;
            case  ContractClass.INFANT_BRACKET:
                weight = (age + 8) / 2;
                break;
            case ContractClass.CHILD_BRACKET:
                if (age < 7) {
                    weight = (age * 2) + 8;
                } else {
                    weight = ((7 * age) - 5) / 2;
                }
                break;
        }

        return weight;
    }

}
