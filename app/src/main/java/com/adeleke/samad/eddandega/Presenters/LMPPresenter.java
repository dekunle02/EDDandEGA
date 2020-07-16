package com.adeleke.samad.eddandega.Presenters;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

import static com.adeleke.samad.eddandega.Contract.ContractClass.DateType.LMP;

public class LMPPresenter implements ContractInterface.LMPPresenter {

    private ContractInterface.LMPView lmpView;
    private LocalDate lmp, edd;
    private String ega;
    private LocalDate today;

    public LMPPresenter(ContractInterface.LMPView lmpView) {
        this.lmpView = lmpView;
        this.today = LocalDate.now();
    }

    @Override
    public String[] calculate(ContractClass.DateType dateType, LocalDate datePicked) {
        if (dateType == LMP) {
            lmp = datePicked;
            ega = calculateEGA(datePicked);
            edd = calculateEDD(datePicked);
        } else {
            lmp = calculateLMPfromEDD(datePicked);
            ega = calculateEGA(lmp);
            edd = datePicked;
        }
        String fullLmp = "LMP -> " + lmp.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String fullEDD = "EDD -> " + edd.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

        return new String[]{fullLmp, ega, fullEDD};
    }

    private LocalDate calculateLMPfromEDD(LocalDate datePicked) {
        return datePicked.minusDays(280);
    }

    private LocalDate calculateEDD(LocalDate datePicked) {
        return datePicked.plusDays(280);
    }


    private String calculateEGA(LocalDate datePicked) {
        long totalDays = ChronoUnit.DAYS.between(datePicked, today);
        int weeks = (int) totalDays / 7;
        int days = (int) totalDays - (weeks * 7);
        return Integer.toString(weeks) + "weeks " + Integer.toString(days) + "days";
    }

}
