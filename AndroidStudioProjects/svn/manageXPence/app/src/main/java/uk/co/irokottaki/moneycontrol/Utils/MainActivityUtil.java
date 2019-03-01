package uk.co.irokottaki.moneycontrol.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import uk.co.irokottaki.moneycontrol.activity.BudgetActivity;
import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;

import static uk.co.irokottaki.moneycontrol.utils.Constants.APRIL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.AUGUST;
import static uk.co.irokottaki.moneycontrol.utils.Constants.BUDGETVALUE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.COUNCIL_TAX;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DECEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DESCRIPTIONS_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ENTERTAINMENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FEBRUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_BILLS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_RENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INCOME;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JANUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JULY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JUNE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MARCH;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MAY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MORTGAGE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.NOVEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OCTOBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PREFERENCES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SEPTEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SHOPPING;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SUPERMARKET;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TRAVEL;

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


    public MainActivityUtil(Context context){

        this.context = context;
    }

    public double processBalance(EditText incomeField, Map<String, AnyYear> yearsMappedToObjectYearsMap, boolean isPaymentCircleSet, String currentMonth, int year) {

        balance = 0.0;

        if (incomeField != null && incomeField.getText().toString().matches("\\d+")) {
            String incomeValue = incomeField.getText().toString();

            AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
            monthSum = 0.0;
            if (currentYear != null) {

                processBalanceForMonth(isPaymentCircleSet, currentMonth, JANUARY, currentYear.getYear().getAmountJan(), currentYear.getYear().getArrayOfamountJan());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, FEBRUARY, currentYear.getYear().getAmountFeb(), currentYear.getYear().getArrayOfamountFeb());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, MARCH, currentYear.getYear().getAmountMar(), currentYear.getYear().getArrayOfamountMar());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, APRIL, currentYear.getYear().getAmountApr(), currentYear.getYear().getArrayOfamountApr());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, MAY, currentYear.getYear().getAmountMay(), currentYear.getYear().getArrayOfamountMay());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, JUNE, currentYear.getYear().getAmountJun(), currentYear.getYear().getArrayOfamountJun());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, JULY, currentYear.getYear().getAmountJul(), currentYear.getYear().getArrayOfamountJul());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, AUGUST, currentYear.getYear().getAmountAug(), currentYear.getYear().getArrayOfamountAug());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, SEPTEMBER, currentYear.getYear().getAmountSep(), currentYear.getYear().getArrayOfamountSep());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, OCTOBER, currentYear.getYear().getAmountOct(), currentYear.getYear().getArrayOfamountOct());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, NOVEMBER, currentYear.getYear().getAmountNov(), currentYear.getYear().getArrayOfamountNov());
                processBalanceForMonth(isPaymentCircleSet, currentMonth, DECEMBER, currentYear.getYear().getAmountDec(), currentYear.getYear().getArrayOfamountDec());
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

    private void processBalanceForMonth(boolean isPaymentCircleSet, String currentMonth, String month, Float amountForMonth, List<Float> arrayOfamount) {
        if (currentMonth.equals(month)) {
            if (isPaymentCircleSet) {
                monthSum = amountForMonth;
            } else {
                sumExpensesForBalance(arrayOfamount);
            }
        }
    }

    private void sumExpensesForBalance(List<Float> arrayOfamount) {
        Float monthSumFloat = 0f;

        for (int i = 0; i < arrayOfamount.size(); i++) {
            monthSumFloat += arrayOfamount.get(i);
        }

        monthSum = monthSumFloat;
    }

    public double checkBudgetWarning(Map<String, AnyYear> yearsMappedToObjectYearsMap, String currentMonth, int year) {

        int sum = 0;
        //get the budget value as stored in Preferences in Budget Activity
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, BudgetActivity.MODE_PRIVATE);
        int progressValue = sp.getInt(BUDGETVALUE, 0);

        AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
        double percentWarning = 0.0;

        //1. Check if the user has enabled this feature from settings.2.if yes find the expenses
        // that have been added so far
        if (progressValue > 0) {
            sum = checkBudgetForMonth(currentMonth, sum, JANUARY, currentYear.getYear().getArrayOfamountJan());
            sum = checkBudgetForMonth(currentMonth, sum, FEBRUARY, currentYear.getYear().getArrayOfamountFeb());
            sum = checkBudgetForMonth(currentMonth, sum, MARCH, currentYear.getYear().getArrayOfamountMar());
            sum = checkBudgetForMonth(currentMonth, sum, APRIL, currentYear.getYear().getArrayOfamountApr());
            sum = checkBudgetForMonth(currentMonth, sum, MAY, currentYear.getYear().getArrayOfamountMay());
            sum = checkBudgetForMonth(currentMonth, sum, JUNE, currentYear.getYear().getArrayOfamountJun());
            sum = checkBudgetForMonth(currentMonth, sum, JULY, currentYear.getYear().getArrayOfamountJul());
            sum = checkBudgetForMonth(currentMonth, sum, AUGUST, currentYear.getYear().getArrayOfamountAug());
            sum = checkBudgetForMonth(currentMonth, sum, SEPTEMBER, currentYear.getYear().getArrayOfamountSep());
            sum = checkBudgetForMonth(currentMonth, sum, OCTOBER, currentYear.getYear().getArrayOfamountOct());
            sum = checkBudgetForMonth(currentMonth, sum, NOVEMBER, currentYear.getYear().getArrayOfamountNov());
            sum = checkBudgetForMonth(currentMonth, sum, DECEMBER, currentYear.getYear().getArrayOfamountDec());
            percentWarning = (double) sum / (double) progressValue;
        }
        return percentWarning;
    }

    private int checkBudgetForMonth(String currentMonth, int sum, String month, List<Float> arrayOfamount) {
        if (currentMonth.equals(month)) {
            for (int i = 0; i < arrayOfamount.size(); i++) {
                sum += arrayOfamount.get(i);
            }
        }
        return sum;
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

    public boolean processExpenseRepeatingAmount(String amount, String description, CheckBox repeatCheckBox, List datesTheRepeatingExpenseOccurs, Activity activity) {

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

    public List populateDatesTheRepeatingExpenseOccursList(int yearToEnd, int monthToEnd, int dayToEnd, int yearX, int monthX) {

        ArrayList datesTheRepeatingExpenseOccurs = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearToEnd, monthToEnd, dayToEnd);

        SimpleDateFormat formatForEndDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String dateToEndRepeatingExpense = formatForEndDate.format(calendar.getTime());

        Log.i("End Date Selected: ",dateToEndRepeatingExpense);
        /* Iterate over from the ending year selected by user. if monthToEnd is greater than the current month then add it to the list */
        while (yearToEnd >= yearX) {

            if (monthToEnd > monthX) {
                Date result = calendar.getTime();
                datesTheRepeatingExpenseOccurs.add(result);

                calendar.add(Calendar.MONTH, -1);

                monthToEnd--;
            }
            else{
                yearToEnd--;
                /*if monthToEnd is equal to current month then i decrease the year by one and set the monthToEnd to 13 so that it will go to the first if
                * for the rest of the months. When it finishes yearToEnd will be decreased again by one and will exit the while loop.*/
                monthToEnd = 12+monthX;
            }
        }
        Collections.reverse(datesTheRepeatingExpenseOccurs);

        return datesTheRepeatingExpenseOccurs;
    }

    @TargetApi(19)
    public List readDescriptionsFile() {

        ArrayList itemsAddedByUser = new ArrayList();
        itemsAddedByUser.add(HOUSE_RENT);
        itemsAddedByUser.add(SHOPPING);
        itemsAddedByUser.add(SUPERMARKET);
        itemsAddedByUser.add(TRAVEL);
        itemsAddedByUser.add(MORTGAGE);
        itemsAddedByUser.add(COUNCIL_TAX);
        itemsAddedByUser.add(HOUSE_BILLS);
        itemsAddedByUser.add(ENTERTAINMENT);

        try (Scanner in = new Scanner(context.openFileInput(DESCRIPTIONS_FILE));){


            while (in.hasNextLine()) {
                String descriptionItem = in.nextLine();
                itemsAddedByUser.add(descriptionItem);
            }

        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }

    return itemsAddedByUser;
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
