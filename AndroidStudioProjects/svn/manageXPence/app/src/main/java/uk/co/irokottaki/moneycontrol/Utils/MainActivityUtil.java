package uk.co.irokottaki.moneycontrol.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.irokottaki.moneycontrol.activity.BudgetActivity;
import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;

import static uk.co.irokottaki.moneycontrol.utils.Constants.APRIL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.AUGUST;
import static uk.co.irokottaki.moneycontrol.utils.Constants.BUDGETVALUE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DECEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ENTERTAINMENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FEBRUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INCOME;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JANUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JULY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JUNE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MARCH;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MAY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.NOVEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OCTOBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PREFERENCES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SEPTEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SHOPPING;

public class MainActivityUtil {
    private Context context;
    private double monthSum;
    private double balance;
    private double incomeDouble;
    private boolean expenseFound;
    private boolean dateFound;
    private boolean descFound;
    private String expense;
    private String description;
    private String date;
    private ArrayList datesTheRepeatingExpenseOccurs;


    public MainActivityUtil(Context context){

        this.context = context;
    }

    public double processBalance(EditText incomeField, HashMap<String, AnyYear> yearsMappedToObjectYearsMap, boolean isPaymentCircleSet, String currentMonth, int year) {

        balance = 0.0;

        if (incomeField != null && incomeField.getText().toString().matches("\\d+")) {
            String incomeValue = incomeField.getText().toString();

            AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
            monthSum = 0.0;
            if (currentYear != null) {

                if (currentMonth.equals(JANUARY)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountJan();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountJan());
                    }
                }
                if (currentMonth.equals(FEBRUARY)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountFeb();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountFeb());
                    }
                }
                if (currentMonth.equals(MARCH)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountMar();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountMar());
                    }
                }
                if (currentMonth.equals(APRIL)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountApr();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountApr());
                    }
                }
                if (currentMonth.equals(MAY)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountMay();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountMay());
                    }
                }
                if (currentMonth.equals(JUNE)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountJun();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountJun());
                    }
                }
                if (currentMonth.equals(JULY)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountJul();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountJul());
                    }
                }
                if (currentMonth.equals(AUGUST)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountAug();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountAug());
                    }
                }
                if (currentMonth.equals(SEPTEMBER)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountSep();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountSep());
                    }
                }
                if (currentMonth.equals(OCTOBER)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountOct();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountOct());
                    }
                }
                if (currentMonth.equals(NOVEMBER)) {
                    if (isPaymentCircleSet) {
                        monthSum = currentYear.getYear().getAmountNov();
                    } else {
                        sumExpensesForBalance(currentYear.getYear().getArrayOfamountNov());
                    }
                }
                if (currentMonth.equals(DECEMBER)) {
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

    public boolean tokenExists() {

        SharedPreferences prefs = context.getSharedPreferences("com.example.valdio.dropboxintegration",
                Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);

        return accessToken != null;
    }

    public String retrieveAccessToken() {
        //check if accessToken is stored on previous app launches
        SharedPreferences prefs = context.getSharedPreferences("com.example.valdio.dropboxintegration",
                Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        if (accessToken == null) {
            Log.d("AccessToken Status", "No token found");
            return null;
        } else {
            //accessToken already exists
            Log.d("AccessToken Status", "Token exists");
            return accessToken;
        }
    }

    public void setExpenseFieldsFromCameraExtractedText(String textResponseFromHaven, Activity activity) {

        expense = "";
        description = "";
        date = "";

        if (textResponseFromHaven == null || textResponseFromHaven.isEmpty()
                || !textResponseFromHaven.replaceAll("\\s+", "").matches(".*[a-zA-Z]+.*")) {
            // response is null so do nothing, print a message to user to take
            // another picture
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle("Did not capture that. Try again!")
                    .setMessage("There was nothing captured from your image or the characters are" +
                            " not recognisable. " +
                            "Please take a better resolution photo!");
            AlertDialog alert1;
            builder.setPositiveButton(CLOSE,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();
        }
        // at this point check if the output contains text characters. Remove all whitespaces and
        // new line and check
        else if (textResponseFromHaven.replaceAll("\\s+", " ").matches(".*[a-zA-Z]+.*")) {

            processWordForExpenses(textResponseFromHaven);

            processWordForDates(textResponseFromHaven);

            processWordForDescription(textResponseFromHaven);
        }
    }

    private void processWordForDescription(String textResponseFromHaven) {

        String[] wordForSuperMarket = new String[]{"Tesco", "Groceries", "Morrisons", "Asda",
                "Lidl", "Waitrose", "Sandwich"};
        String[] wordForEntertainment = new String[]{"Course", "Table", "Drink",
                "Restaurants", "Coffee"};
        String[] wordForShopping = new String[]{"Jacket", "Trouser", "Cycles", "Medium",
                "Large", "Small",};
        String[] wordForPetrol = new String[]{"Unleaded", "Petrol", "Petroleum"};
        for (String aWordForSuperMarket : wordForSuperMarket) {
            if (textResponseFromHaven.contains(aWordForSuperMarket)) {
                // get the description
                description = "Supermarket";
                descFound = true;
                break;
            }
        }
        for (String aWordForEntertainment : wordForEntertainment) {
            if (textResponseFromHaven.contains(aWordForEntertainment)) {
                description = ENTERTAINMENT;
                descFound = true;
                break;
            }
        }
        for (String aWordForShopping : wordForShopping) {
            if (textResponseFromHaven.contains(aWordForShopping)) {
                description = SHOPPING;
                descFound = true;
                break;
            }
        }
        for (String aWordForPetrol : wordForPetrol) {
            if (textResponseFromHaven.contains(aWordForPetrol)) {
                description = "Petrol";
                descFound = true;
                break;
            }
        }
    }

    private void processWordForDates(String textResponseFromHaven) {

        String[] wordForDates = new String[]{"Date:", DATE};

        for (String wordForDate : wordForDates) {
            //check if the word date exists in the receipt
            if (textResponseFromHaven.toLowerCase(Locale.ENGLISH).contains(wordForDate
                    .toLowerCase(Locale.US))) {
                //check if the next word after the word Date is a valid date like mm/dd/yyyy
                String[] split = textResponseFromHaven.toLowerCase(Locale.ENGLISH).split
                        (wordForDate.toLowerCase(Locale.US).trim());
                String potentialDate = split[1].trim().substring(0, split[1].trim().indexOf("" + " "));

                // if the date string checked is null then date not found in the Map
                if (findTheDateFormat(potentialDate) == null) {
                    date = null;
                }
                // if it is a valid date then return it. Check
                // substring after the word Date and count the length of the string returned
                // from the findTheDateFormat
                else {
                    date = potentialDate;
                    dateFound = true;
                    break;
                }
                Log.e("Date Found in RECEIPT: ", date);
                // get the date
                //NEED TO MAKE SURE THAT THE WORDSFORDATES IS THE ACTUAL DATE AND NOT THE
                // WORD DATE/

            } else {
                String[] splitResponseToStrings = textResponseFromHaven.replaceAll("\n", "")
                        .split(" ");
                for (int j = 0; j < splitResponseToStrings.length; j++) {
                    if (splitResponseToStrings[j].contains("/")) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy", Locale
                                .US);
                        splitResponseToStrings[j] = formatter.format(splitResponseToStrings[j]);
                    }
                    if (findTheDateFormat(splitResponseToStrings[j]) != null) {
                        date = splitResponseToStrings[j];
                    }
                }
            }
        }
    }

    private void processWordForExpenses(String textResponseFromHaven) {
        String[] wordForExpenses = new String[]{"total", "amount", "subtotal", "Visa Debit " +
                "£", "total sale:", "total gbp"};

        for (String wordForExpense : wordForExpenses) {
            if (textResponseFromHaven.toLowerCase(Locale.ENGLISH).contains(wordForExpense)) {
                // get the expense
                //it is the substring of TOTAL plus one char and tha next space found in the
                // response
                String[] split = textResponseFromHaven.toLowerCase(Locale.ENGLISH).split
                        (wordForExpense.toLowerCase(Locale.US));
                String amount = split[1].trim();
                //check that the first character is number
                if (Character.isDigit(amount.trim().charAt(0))) {
                    int j = 0;
                    for (int k = 0; k < amount.length(); k++) {
                        Character charToCheck = amount.charAt(k);
                        //if the character is not a number and is one of . - space
                        if (!Character.isDigit(charToCheck) &&
                                charToCheck.toString().equals(".") || charToCheck.toString()
                                .equals("-")
                                || charToCheck.toString().equals(" ")) {
                            expense = expense.concat(".");
                            j++;
                        } else if (Character.isDigit(charToCheck)) {
                            expense = expense.concat(String.valueOf(Character.getNumericValue(amount
                                    .trim().charAt(j))));
                            expenseFound = true;
                            j++;
                        }
                    }
                }
            }
        }
    }

    public static String findTheDateFormat(String dateString) {

        String dateStringFound = "";

        Map<String, String> dateFormatRegexps = new HashMap<>();
        dateFormatRegexps.put("^\\d{8}$", "yyyyMMdd");
        dateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        dateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        dateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        dateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{2}$", "dd/MM/yy");
        dateFormatRegexps.put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        dateFormatRegexps.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        dateFormatRegexps.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        dateFormatRegexps.put("^\\d{12}$", "yyyyMMddHHmm");
        dateFormatRegexps.put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        dateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        dateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        dateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        dateFormatRegexps.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        dateFormatRegexps.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        dateFormatRegexps.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        dateFormatRegexps.put("^\\d{14}$", "yyyyMMddHHmmss");
        dateFormatRegexps.put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        dateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        dateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        dateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        dateFormatRegexps.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        dateFormatRegexps.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        dateFormatRegexps.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy " + "HH:mm:ss");

        for (String regexp : dateFormatRegexps.keySet()) {
            if (dateString.toLowerCase(Locale.ENGLISH).matches(regexp)) {

                dateStringFound = dateFormatRegexps.get(regexp);
                break;
            } else {
                dateStringFound = null;// unknown format so handle it in the call

            }
        }

        return dateStringFound;
    }

    public boolean processExpenseRepeatingAmount(String amount, String description, CheckBox repeatCheckBox, ArrayList datesTheRepeatingExpenseOccurs, Activity activity) {

        boolean expenseIsAdded = false;

        if(repeatCheckBox.isChecked()){

            for (Object dateThatExpenseOcurrs: datesTheRepeatingExpenseOccurs){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String dateThatExpenseOccursString = formatter.format(dateThatExpenseOcurrs);

                ((MainActivity)activity).writeToFile(amount,description, dateThatExpenseOccursString);
                expenseIsAdded = true;
            }
        }
        return expenseIsAdded;
    }

    public double getBalance() {
        return balance;
    }

    public double getIncomeDouble() {
        return incomeDouble;
    }

    public boolean isExpenseFound() {
        return expenseFound;
    }

    public boolean isDateFound() {
        return dateFound;
    }

    public boolean isDescFound() {
        return descFound;
    }

    public String getExpense() {
        return expense;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
