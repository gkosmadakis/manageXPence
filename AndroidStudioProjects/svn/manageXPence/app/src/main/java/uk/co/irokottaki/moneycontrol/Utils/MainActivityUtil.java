package uk.co.irokottaki.moneycontrol.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.activity.BudgetActivity;
import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;

public class MainActivityUtil {
    private static Float incomeForJan;
    private static Float incomeForFeb;
    private static Float incomeForMar;
    private static Float incomeForApr;
    private static Float incomeForMay;
    private static Float incomeForJun;
    private static Float incomeForJul;
    private static Float incomeForAug;
    private static Float incomeForSep;
    private static Float incomeForOct;
    private static Float incomeForNov;
    private static Float incomeForDec;
    private Context context;
    private double monthSum;
    private double balance;
    private double incomeDouble;


    public MainActivityUtil(Context context){

        this.context = context;
    }

    public double processBalance(EditText incomeField, HashMap<String, AnyYear> yearsMappedToObjectYearsMap, boolean isPaymentCircleSet, String currentMonth, int year) {

        balance = 0.0;

        if (incomeField != null && incomeField.getText().toString().matches("\\d+")) {
            String incomeValue = incomeField.getText().toString();
            incomeForJan = Float.parseFloat(incomeValue);//i initialize the incomes for every
            // month to avoid null values
            incomeForFeb = Float.parseFloat(incomeValue);// returned to AnnualSavingsActivity. So
            // if the user will not
            incomeForMar = Float.parseFloat(incomeValue);//change the income then i use the
            // current that has been entered
            incomeForApr = Float.parseFloat(incomeValue);// if on a month the user changes the
            // income then this will be
            incomeForMay = Float.parseFloat(incomeValue);// changed above on the if statements.
            incomeForJun = Float.parseFloat(incomeValue);
            incomeForJul = Float.parseFloat(incomeValue);
            incomeForAug = Float.parseFloat(incomeValue);
            incomeForSep = Float.parseFloat(incomeValue);
            incomeForOct = Float.parseFloat(incomeValue);
            incomeForNov = Float.parseFloat(incomeValue);
            incomeForDec = Float.parseFloat(incomeValue);


            AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
            monthSum = 0.0;
            if (currentYear != null) {

                if (currentMonth.equals(JANUARY)) {
                    incomeForJan = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountJan();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountJan());
                    }
                }
                if (currentMonth.equals(FEBRUARY)) {
                    incomeForFeb = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountFeb();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountFeb());
                    }
                }
                if (currentMonth.equals(MARCH)) {
                    incomeForMar = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountMar();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountMar());
                    }
                }
                if (currentMonth.equals(APRIL)) {
                    incomeForApr = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountApr();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountApr());
                    }
                }
                if (currentMonth.equals(MAY)) {
                    incomeForMay = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountMay();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountMay());
                    }
                }
                if (currentMonth.equals(JUNE)) {
                    incomeForJun = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountJun();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountJun());
                    }
                }
                if (currentMonth.equals(JULY)) {
                    incomeForJul = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountJul();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountJul());
                    }
                }
                if (currentMonth.equals(AUGUST)) {
                    incomeForAug = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountAug();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountAug());
                    }
                }
                if (currentMonth.equals(SEPTEMBER)) {
                    incomeForSep = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountSep();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountSep());
                    }
                }
                if (currentMonth.equals(OCTOBER)) {
                    incomeForOct = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountOct();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountOct());
                    }
                }
                if (currentMonth.equals(NOVEMBER)) {
                    incomeForNov = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountNov();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountNov());
                    }
                }
                if (currentMonth.equals(DECEMBER)) {
                    incomeForDec = Float.parseFloat(incomeValue);
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountDec();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountDec());
                    }
                }
            }
            // this is to avoid invalid double thrown on initial state where income is not added
            // yet by the user
            incomeDouble = Double.valueOf(incomeValue);
            balance = incomeDouble - monthSum;

            //store it in preferences
            SharedPreferences sp = context.getSharedPreferences(PREFERENCES, MainActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(INCOME, incomeValue);
            editor.apply();
        }
        return balance;
    }

    private void sumExpensesForBalance(ArrayList<Float> arrayOfamount) {
        Float monthSumFloat = 0f;

        for (int i = 0; i < arrayOfamount.size(); i++) {
            monthSumFloat += arrayOfamount.get(i);
        }

        monthSum = monthSumFloat;
    }

    public double checkBudgetWarning(HashMap<String, AnyYear> yearsMappedToObjectYearsMap, String currentMonth, int year) {

        int sum = 0;
        //get the budget value as stored in Preferences in Budget Activity
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, BudgetActivity.MODE_PRIVATE);
        int progressValue = sp.getInt(BUDGETVALUE, 0);

        AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
        double percentWarning = 0.0;

        //1. Check if the user has enabled this feature from settings.2.if yes find the expenses
        // that have been added so far
        if (progressValue > 0) {
            if (currentMonth.equals(JANUARY)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountJan().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountJan().get(i);
                }

            }
            if (currentMonth.equals(FEBRUARY)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountFeb().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountFeb().get(i);
                }

            }
            if (currentMonth.equals(MARCH)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountMar().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountMar().get(i);
                }

            }
            if (currentMonth.equals(APRIL)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountApr().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountApr().get(i);
                }

            }
            if (currentMonth.equals(MAY)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountMay().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountMay().get(i);
                }

            }
            if (currentMonth.equals(JUNE)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountJun().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountJun().get(i);
                }

            }
            if (currentMonth.equals(JULY)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountJul().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountJul().get(i);
                }

            }
            if (currentMonth.equals(AUGUST)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountAug().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountAug().get(i);
                }

            }
            if (currentMonth.equals(SEPTEMBER)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountSep().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountSep().get(i);
                }

            }
            if (currentMonth.equals(OCTOBER)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountOct().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountOct().get(i);
                }

            }
            if (currentMonth.equals(NOVEMBER)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountNov().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountNov().get(i);
                }

            }
            if (currentMonth.equals(DECEMBER)) {
                for (int i = 0; i < currentYear.getYear().getArrayOfamountDec().size(); i++) {
                    sum += currentYear.getYear().getArrayOfamountDec().get(i);
                }

            }
            percentWarning = (double) sum / (double) progressValue;
        }
        return percentWarning;
    }

    public double getBalance() {
        return balance;
    }

    public double getIncomeDouble() {
        return incomeDouble;
    }

    public static Float getIncomeForJan() {
        return incomeForJan;
    }

    public static Float getIncomeForFeb() {
        return incomeForFeb;
    }

    public static Float getIncomeForMar() {
        return incomeForMar;
    }

    public static Float getIncomeForApr() {
        return incomeForApr;
    }

    public static Float getIncomeForMay() {
        return incomeForMay;
    }

    public static Float getIncomeForJun() {
        return incomeForJun;
    }

    public static Float getIncomeForJul() {
        return incomeForJul;
    }

    public static Float getIncomeForAug() {
        return incomeForAug;
    }

    public static Float getIncomeForSep() {
        return incomeForSep;
    }

    public static Float getIncomeForOct() {
        return incomeForOct;
    }

    public static Float getIncomeForNov() {
        return incomeForNov;
    }

    public static Float getIncomeForDec() {
        return incomeForDec;
    }

}
