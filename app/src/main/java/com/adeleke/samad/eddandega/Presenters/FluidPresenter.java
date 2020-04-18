package com.adeleke.samad.eddandega.Presenters;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;

public class FluidPresenter implements ContractInterface.FluidPresenter{

    ContractInterface.FluidView view;

    public FluidPresenter(ContractInterface.FluidView view) {
        this.view = view;
    }


    @Override
    public double calculateMaintenance(double weight) {
        double maintenance;
        if (weight <= 10){
            maintenance = weight * 100;
        }else if (weight >10 && weight<= 20){
            maintenance = (1000 + ((weight-10)*50));
        }else{
            maintenance = (1500 + ((weight-20)*20));
        }

        return maintenance;
    }


    @Override
    public double calculateDeficit(double weight, double deficit) {
        double deficitFluid;
        if (deficit == ContractClass.MILD_DEHYDRATION){
            deficitFluid = weight * ContractClass.MILD_DEHYDRATION;
        }else if (deficit == ContractClass.MODERATE_DEHYDRATION){
            deficitFluid = weight * ContractClass.MODERATE_DEHYDRATION;
        }else if (deficit == ContractClass.SEVERE_DEHYDRATION) {
            deficitFluid = weight * ContractClass.SEVERE_DEHYDRATION;
        }else{
            deficitFluid = 0;
        }
        return deficitFluid;
    }
}
