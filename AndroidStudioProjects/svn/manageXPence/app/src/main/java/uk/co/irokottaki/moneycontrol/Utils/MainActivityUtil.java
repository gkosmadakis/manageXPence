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
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.activity.BudgetActivity;
import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;

import static uk.co.irokottaki.moneycontrol.utils.Constants.APRIL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.AUGUST;
import static uk.co.irokottaki.moneycontrol.utils.Constants.BUDGETVALUE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.COUNCIL_TAX;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DATE_FORMAT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DECEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DESCRIPTIONS_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ENTERTAINMENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FEBRUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_BILLS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_RENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INCOME;
import static uk.co.irokottaki.moneycontrol.utils.Constants.IOEXCEPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JANUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JULY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JUNE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MARCH;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MAY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MORTGAGE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.NOVEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OCTOBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OK;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PARSE_EXCEPTION;
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
                Log.i("Date Found in RECEIPT: ", date);
                // get the date
                //NEED TO MAKE SURE THAT THE WORDSFORDATES IS THE ACTUAL DATE AND NOT THE
                // WORD DATE/

            } else {
                processWordForDatesBasedOnSlash(textResponseFromHaven);
            }
        }
    }

    private void processWordForDatesBasedOnSlash(String textResponseFromHaven) {
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
                    processWordForExpenseDigit(amount);
                }
            }
        }
    }

    private void processWordForExpenseDigit(String amount) {
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

    public static String findTheDateFormat(String dateString) {

        String dateStringFound = "";

        Map<String, String> dateFormatRegexps = new HashMap<>();
        dateFormatRegexps.put("^\\d{8}$", "yyyyMMdd");
        dateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        dateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        dateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        dateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{2}$", DATE_FORMAT);
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

        for (Map.Entry<String, String> entry : dateFormatRegexps.entrySet()) {
            if (dateString.toLowerCase(Locale.ENGLISH).matches(entry.getKey())) {

                dateStringFound = dateFormatRegexps.get(entry.getKey());
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
            Log.e(IOEXCEPTION, e.getMessage());
        }

    return itemsAddedByUser;
    }

    public List getDaysBetweenDates(Activity activity, String datesFromTo) {

        ArrayList dates = new ArrayList<>();//the arraylist where i store the dates

        if (validateDatesFromToField(activity, datesFromTo)) {
            return dates;
        }

        String[] token = datesFromTo.split("-");//store dates splitted by -
        String firstdate = token[0];//take the first date entered by the user
        String lastdate = token[1];//take the second date entered by the user

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date startdate;
        startdate = null;

        if (!firstdate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")
                || !lastdate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity,
                    R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle("Wrong Format!")
                    .setMessage("Dates should be entered in the dd/MM/yyyy format.Please " +
                            "supply a correct date range");
            builder.setPositiveButton(OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
        } else {

            try {
                startdate = format.parse(firstdate);
            } catch (ParseException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity,
                        R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("Wrong Format!")
                        .setMessage("Please supply a correct date range.");
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
                Log.e(PARSE_EXCEPTION, e.getMessage());
            }
            Date endDate;
            endDate = null;
            try {
                endDate = format.parse(lastdate);//convert the second date into Date

            } catch (ParseException e) {

                Log.e(PARSE_EXCEPTION, e.getMessage());
            }

            Calendar calendarForDate = Calendar.getInstance();
            calendarForDate.setTime(startdate);

            if (endDate != null) {
                while (calendarForDate.getTime().getTime() <= endDate.getTime()) {
                    Date result = calendarForDate.getTime();//take the date
                    dates.add(result);//add it to dates arraylist
                    calendarForDate.add(Calendar.DATE, 1);
                }
            }

        }
        // 15/09/2015-17/09/2015 dates will store 15/09/2015 16/09/2015 17/09/2015
        return dates;
    }

    public boolean validateDatesFromToField(Activity activity, String datesFromTo) {

        boolean isNotValid = false;
        if (datesFromTo.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle("Empty Field!")
                    .setMessage("Add expenses field is empty.Please supply a date range");
            builder.setPositiveButton(OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
            isNotValid = true;
        } else if (!datesFromTo.matches("^[0-9].*") || !datesFromTo.contains("-")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle("Wrong Date!")
                    .setMessage("You entered words for a date or a single date. " +
                            "Please supply a correct date range in the format dd/MM/yyyy");
            builder.setPositiveButton(OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
            isNotValid = true;
        }
        return isNotValid;
    }

    @TargetApi(19)
    public double addTheExpenses(Activity activity, String datesFromTo, Spinner addExpensesByDescription) {

        double sum = 0.0;
        ArrayList dates = (ArrayList<Date>) getDaysBetweenDates(activity, datesFromTo);
        if(dates.isEmpty()) {
            return sum;
        }

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        Date dateIntheFile = null;
        Set<Date> datesMatchedUserInput = new TreeSet<>();
        String expenseAmount = null;
        List expensesList = new ArrayList();
        Set uniqueDescriptions = new LinkedHashSet<>();
        List<Float> uniqueAmounts = new ArrayList<>();

        String desc = "";
        String dateLocal = "";

        try (InputStream inputStream = context.openFileInput(EXPENSES_FILE);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             Scanner in = new Scanner(inputStreamReader);) {

            //read the file
            int lineIndex = 0;//this is to count the lines
            while (in.hasNextLine()) {
                String line = in.nextLine();
                //i need to read after the first line
                if (++lineIndex > 2 && !line.equals("")) {

                    expenseAmount = line.substring(0, line.indexOf(' '));//take the amount
                    // from the file
                    int index = line.lastIndexOf(' ');
                    desc = line.substring(line.indexOf(' '), index).trim();//take the
                    // description
                    dateLocal = line.substring(index, line.length());//take the date
                    dateIntheFile = getDate(format, dateIntheFile, dateLocal);

                    double firstDateAmountNumber;
                    if (dates.contains(dateIntheFile)) {
                        datesMatchedUserInput.add(dateIntheFile);//add the Date in a list
                        // that will maintain them as the while loop checks all the dates.
                        String firstDateAmount = expenseAmount;//if the dates taken from the
                        // input are the same
                        firstDateAmountNumber = Double.parseDouble(firstDateAmount);//with
                        // those in the file
                        expensesList.add(firstDateAmountNumber);//then look each line and find
                        // the amount given. Add the amount in the list of expenses.

                        //process the addition of expenses for a description
                        processDuplicateAmounts(expenseAmount, uniqueDescriptions, uniqueAmounts, desc);
                    }
                }
            }// end of while
            Collections.sort(dates);
            if (datesMatchedUserInput.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("No matches found")
                        .setMessage("The are no expenses in the dates you entered! Please try" +
                                " with different dates.");
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            Date nextValue = null;
            for (Date aDatesMatchedUserInput : datesMatchedUserInput) {
                nextValue = aDatesMatchedUserInput;
            }
            if (dates.contains(nextValue) && addExpensesByDescription.getSelectedItemPosition
                    () == 0) {
                sum = sumTheExpenses(datesFromTo, activity, expensesList);//this sums up all the expenses for the date range. it is
                // not inside the other if
                // because it would pop up all the
                // times the while loop is counting
            } else if (dates.contains(nextValue) && addExpensesByDescription
                    .getSelectedItemPosition() > 0) {
                String descSelected = addExpensesByDescription.getSelectedItem().toString();
                sum = sumTheExpensesByDescription(descSelected, activity, datesFromTo, addExpensesByDescription, uniqueDescriptions, uniqueAmounts);
            }

        } catch (IOException e) {
            Log.e("Main activity util", "Can not read file: " + e.toString());
        }

        return sum;
    }

    private void processDuplicateAmounts(String expenseAmount, Set uniqueDescriptions, List<Float> uniqueAmounts, String desc) {
        if (uniqueDescriptions.contains(desc)) {
            int i = 0;
            for (Iterator<String> s = uniqueDescriptions.iterator(); s
                    .hasNext(); i++) {
                String descriptionFound = s.next();
                if (desc.equals(descriptionFound)) {
                    Float amountWithDuplicate = uniqueAmounts.get(i) + Float
                            .valueOf(expenseAmount);//add up the amounts if there are duplicates
                    uniqueAmounts.set(i, amountWithDuplicate);
                }
            }
        } else {
            uniqueDescriptions.add(desc);
            uniqueAmounts.add(Float.valueOf(expenseAmount));
        }
    }

    public double sumTheExpensesByDescription(String desc, Activity activity, String datesFromTo, Spinner addExpensesByDescription, Set uniqueDescriptions, List<Float> uniqueAmounts) {

        Float sumFloat = 0f;
        if (desc.equals(addExpensesByDescription.getSelectedItem().toString())) {
            Iterator<String> itr = uniqueDescriptions.iterator();
            int i = 0;
            while (itr.hasNext()) {
                String descIterated = itr.next();
                if (desc.equals(descIterated)) {
                    sumFloat += uniqueAmounts.get(i);
                }
                i++;
            }
        }

        double sum = sumFloat;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(desc + " expenses for: " + datesFromTo)
                .setMessage("You have spent: " + String.format(Locale.ENGLISH, "%.2f", sum));
        AlertDialog alert1;
        builder.setPositiveButton(OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert1 = builder.create();
        alert1.show();

        return sum;
    }

    public double sumTheExpenses(String datesFromTo, Activity activity, List<Double> expensesList) {

        double sum = 0;
        for (int i = 0; i < expensesList.size(); i++) {
            sum += expensesList.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("Expenses for: " + datesFromTo)
                .setMessage("You have spent: " + String.format(Locale.ENGLISH, "%.2f", sum));
        AlertDialog alert1;
        builder.setPositiveButton(OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert1 = builder.create();
        alert1.show();

        return sum;
    }

    private Date getDate(SimpleDateFormat format, Date dateIntheFile, String date) {

        try {
            dateIntheFile = format.parse(date);//convert the date into Date

        } catch (ParseException e) {

            Log.e(PARSE_EXCEPTION, e.getMessage());
        }
        return dateIntheFile;
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
