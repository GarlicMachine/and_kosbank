package com.teamproject.sample.kosbank.test;

import java.text.DecimalFormat;

public class fmt {
    public static String moneyFormatToWon(int inputMoney) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        return decimalFormat.format(inputMoney);
    }


}
