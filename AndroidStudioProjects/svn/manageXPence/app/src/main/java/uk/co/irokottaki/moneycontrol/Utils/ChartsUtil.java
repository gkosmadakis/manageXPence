package uk.co.irokottaki.moneycontrol.Utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import uk.co.irokottaki.moneycontrol.Activity.AnnualChartActivity;
import uk.co.irokottaki.moneycontrol.Activity.AnnualSavingsActivity;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2015;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2016;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2017;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2018;
import uk.co.irokottaki.moneycontrol.Model.AmountsForYear;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.ANNUAL_EXPENSES;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.APRIL;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.AUGUST;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.DECEMBER;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.EIGHT;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.ELEVEN;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.EXPENSES_FILE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.FEBRUARY;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.FIVE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.FOUR;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.JANUARY;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.JULY;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.JUNE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.MARCH;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.MAY;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.NINE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.NOVEMBER;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.OCTOBER;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.ONE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.SEPTEMBER;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.SEVEN;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.SIX;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TEN;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.THREE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TWELVE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TWO;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.YEAR;

public class ChartsUtil {

    AmountsFor2018 object2018;
    AmountsFor2017 object2017;
    AmountsFor2016 object2016;
    AmountsFor2015 object2015;
    AmountsForYear objectYear;
    private Context context;
    HashMap<String, Map<String, ArrayList<String>>> yearsMappedToMonthsWithAmountsMap;

    public ChartsUtil(Context context) {

        this.context = context;
    }

    public void readTheFile() {

        String amount;
        String date;

        objectYear = new AmountsForYear(object2015, object2016,object2017,object2018);

        object2015 = new AmountsFor2015(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        object2016 = new AmountsFor2016(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        object2017 = new AmountsFor2017(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        object2018 = new AmountsFor2018(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        try {
            InputStream inputStream = context.openFileInput(EXPENSES_FILE);
            Scanner in = new Scanner(inputStream);
            int lineIndex = 0;//this is to count the lines
            yearsMappedToMonthsWithAmountsMap = new HashMap<>();
            Map tempMap = new HashMap<String, ArrayList<String>>();
            List tempList = new ArrayList<String>();
            while (in.hasNextLine()) {

                String line = in.nextLine();
                if (++lineIndex > 2 && !line.equals("")) {
                    int index = line.lastIndexOf(" ");
                    amount = line.substring(0, line.indexOf(" "));
                    date = line.substring(index, line.length());
                    String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date
                            .lastIndexOf("/"));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date
                            .length());
                /*Here I store the data to the map, key is the year, value is another map, the tempMap with key as the month
                * and value an arraylist with the amounts*/
                    if (yearsMappedToMonthsWithAmountsMap.containsKey(extractYearFromDate)){
                    /*if the key = year is in the map then get the tempMap*/
                        tempMap = yearsMappedToMonthsWithAmountsMap.get(extractYearFromDate);
                        /*if the key = month is in the tempMap then get the tempList and add the amount. Store it in the map*/
                        if (tempMap.containsKey(extractMonthFromDate)) {
                            tempList = (List) tempMap.get(extractMonthFromDate);

                            tempList.add(amount);
                            tempMap.put(extractMonthFromDate, tempList);
                            yearsMappedToMonthsWithAmountsMap.put(extractYearFromDate, tempMap);
                        }
                        /*if the key = month is not in the tempMap then create a new tempList and add the amount, finslly store it in the map*/
                        else {
                            tempList = new ArrayList<String>();

                            tempList.add(amount);
                            tempMap.put(extractMonthFromDate, tempList);
                            yearsMappedToMonthsWithAmountsMap.put(extractYearFromDate, tempMap);
                        }
                    }
                    /*if the key = year is not in the map then create a new tempMap, a new tempList add the amount in the tempList
                    * and the tempList in the tempMap. Store them in the map*/
                    else {
                        tempMap = new HashMap<String, ArrayList<String>>();
                        tempList = new ArrayList<String>();

                        tempList.add(amount);
                        tempMap.put(extractMonthFromDate, tempList);
                        yearsMappedToMonthsWithAmountsMap.put(extractYearFromDate, tempMap);
                    }
                }
            }
            inputStream.close();

        } catch (FileNotFoundException e) {
            Log.e("File not found ", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException ", e.getMessage());
        }

        for (Map.Entry<String, Map<String, ArrayList<String>>> yearEntry : yearsMappedToMonthsWithAmountsMap.entrySet()) {
            String year = yearEntry.getKey();
            Log.e("Year from file is: ", year);
            for (Map.Entry<String, ArrayList<String>> monthEntry : yearEntry.getValue().entrySet()) {
                String month = monthEntry.getKey();
                ArrayList amounts = monthEntry.getValue();
                Log.e("Month from file is: ", month);
                processAmountsForEveryMonth(month, amounts, objectYear, year );
                for (Object amountInFile : amounts) {
                    Log.e("Amounts from file is: ", amountInFile.toString());
                }
            }
        }
    }

    private void processAmountsForEveryMonth(String month, ArrayList amounts, AmountsForYear obj, String year ){

        if (month.equals(ONE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountJan(totalExpensesForMonth, year);
        }
        if (month.equals(TWO)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountFeb(totalExpensesForMonth, year);
        }
        if (month.equals(THREE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountMar(totalExpensesForMonth, year);
        }
        if (month.equals(FOUR)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountApr(totalExpensesForMonth, year);
        }
        if (month.equals(FIVE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountMay(totalExpensesForMonth, year);
        }
        if (month.equals(SIX)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountJun(totalExpensesForMonth, year);
        }
        if (month.equals(SEVEN)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountJul(totalExpensesForMonth, year);
        }
        if (month.equals(EIGHT)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountAug(totalExpensesForMonth, year);
        }
        if (month.equals(NINE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountSep(totalExpensesForMonth, year);
        }
        if (month.equals(TEN)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountOct(totalExpensesForMonth, year);
        }
        if (month.equals(ELEVEN)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountNov(totalExpensesForMonth, year);
        }
        if (month.equals(TWELVE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += Float.valueOf((String) amounts.get(i));
            }
            obj.setAmountDec(totalExpensesForMonth, year);
        }
    }

    public AmountsForYear getObjectYear() {
        return objectYear;
    }

    public void setData(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
                        Float aug, Float sep, Float oct, Float nov, Float dec, LineChart mChart) {

        String [] monthData = new String[]{JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER,
                OCTOBER, NOVEMBER, DECEMBER};
        ArrayList<String> xVals = new ArrayList<>();
        for (String aMonthData : monthData) {
            xVals.add((aMonthData).substring(0, 3));
        }

        ArrayList<Entry> yVals = new ArrayList<>();

        ArrayList<Float> addAlltheMonths = new ArrayList<>();
        addAlltheMonths.add(jan);
        addAlltheMonths.add(feb);
        addAlltheMonths.add(mar);
        addAlltheMonths.add(apr);
        addAlltheMonths.add(may);
        addAlltheMonths.add(jun);
        addAlltheMonths.add(jul);
        addAlltheMonths.add(aug);
        addAlltheMonths.add(sep);
        addAlltheMonths.add(oct);
        addAlltheMonths.add(nov);
        addAlltheMonths.add(dec);

        for (int i = 0; i < monthData.length; i++) {
            float val = addAlltheMonths.get(i);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, ANNUAL_EXPENSES);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
        //update chart
        mChart.invalidate();
    }

    public Calendar setXYAxisForChart(TextView yearView, LineChart mChart, int year) {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaxValue(3000f);
        leftAxis.setAxisMinValue(-50f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);// get the current year
        yearView.setText(YEAR + year);
        return calendar;
    }
}
