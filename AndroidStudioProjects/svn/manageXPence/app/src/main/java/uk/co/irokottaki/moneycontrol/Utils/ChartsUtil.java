package uk.co.irokottaki.moneycontrol.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import uk.co.irokottaki.moneycontrol.Activity.CalculateAnnualExpensesActivity;
import uk.co.irokottaki.moneycontrol.Activity.ChartActivity;
import uk.co.irokottaki.moneycontrol.Activity.HorizontalBarChartActivity;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2015;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2016;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2017;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2018;
import uk.co.irokottaki.moneycontrol.Model.AmountsForYear;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class ChartsUtil {

    AmountsFor2018 object2018;
    AmountsFor2017 object2017;
    AmountsFor2016 object2016;
    AmountsFor2015 object2015;
    AmountsForYear objectYear;
    private Context context;
    /*This map has all the data needed from the file. The first  key String on the map is the year, the second map contains the months as key,
    * and another map which contains a set with the descriptions and an arraylist with the expense amounts. An example could be
    * 2018=> 11=> Key: Supermarket-> 25,20,11
				  Key: Shopping-> 10,20
				  Key: House Rent-> 265
	  2017=> 05=> Key: Travel-> 250,200
	              Key: Mortgage-> 400
	              Key: Shopping-> 35, 55,100 */
    private HashMap<String, Map<String, Map<String, ArrayList<Float>>>> yearsMappedToMonthsWithAmountsMap;
    private LinkedHashSet<String> descriptionsSet;
    private HashMap<String, Map<String, String>> yearsMappedToMonthsWithFileLines;
    private String allLinesInFile;

    public ChartsUtil(Context context) {

        this.context = context;
    }

    public void readTheFile() {

        Float amount;
        String desc;
        String date;

        objectYear = new AmountsForYear(object2015, object2016, object2017, object2018);

        object2015 = new AmountsFor2015(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        object2016 = new AmountsFor2016(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        object2017 = new AmountsFor2017(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        object2018 = new AmountsFor2018(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        try {
            InputStream inputStream = context.openFileInput(EXPENSES_FILE);
            Scanner in = new Scanner(inputStream);
            int lineIndex = 0;//this is to count the lines
            yearsMappedToMonthsWithAmountsMap = new HashMap<>();
            Map tempFirstMap = new HashMap<String, Map<String, ArrayList<String>>>();
            Map tempSecondMap = new HashMap<String, ArrayList<String>>();
            ArrayList tempList = new ArrayList<Float>();
            descriptionsSet = new LinkedHashSet<>();
            LinkedHashSet descriptionsSet = new LinkedHashSet<>();
            yearsMappedToMonthsWithFileLines = new HashMap<>();
            Map tempFileLinesMap = new HashMap<String, String>();
            String fileLines = "";
            while (in.hasNextLine()) {

                String line = in.nextLine();
                if (++lineIndex > 2 && !line.equals("")) {
                    int index = line.lastIndexOf(" ");
                    amount = Float.valueOf(line.substring(0, line.indexOf(" ")));
                    desc = line.substring(line.indexOf(" "), index).trim();
                    date = line.substring(index, line.length());
                    String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date
                            .lastIndexOf("/"));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date
                            .length());
                    /*Here I store the data to the map, key is the year, value is another map, the tempMap with key as the month
                     * and value an arraylist with the amounts of this month*/
                    if (yearsMappedToMonthsWithAmountsMap.containsKey(extractYearFromDate)) {
                        /*if the key = year is in the map then get the tempFirstMap*/
                        tempFirstMap = yearsMappedToMonthsWithAmountsMap.get(extractYearFromDate);
                        /*if the key = month is in the tempFirstMap and the descriptionSet has the current description
                        then get the tempList and the tempSecondMap and call the method to add amounts that are with the same description */
                        if (tempFirstMap.containsKey(extractMonthFromDate) && descriptionsSet.contains(desc)) {
                            tempList = (ArrayList) tempSecondMap.get(desc);
                            tempSecondMap = (Map) tempFirstMap.get(extractMonthFromDate);

                            addAmountsWithDuplicates(descriptionsSet,desc, String.valueOf(amount), tempList);
                        }
                        /*if the key = month is in the tempFirstMap but the description is not in the descriptionSet
                        then get the tempSecondMap. Add the amount to the templist, the desc to the descriptionSet */
                        else if (tempFirstMap.containsKey(extractMonthFromDate)) {
                            tempSecondMap = (Map) tempFirstMap.get(extractMonthFromDate);
                            //tempList = new ArrayList<String>();

                            tempList.add(amount);
                            descriptionsSet.add(desc);
                        }
                        /* if the key = month is not in the map at all then get the tempFirstMap, create a new tempSecondMap, a new set and a new list.
                        * Add the amount to the list, the desc in the set */
                        else {
                            tempFirstMap = yearsMappedToMonthsWithAmountsMap.get(extractYearFromDate);
                            tempSecondMap = new HashMap<LinkedHashSet, ArrayList<String>>();
                            descriptionsSet = new LinkedHashSet<>();
                            tempList = new ArrayList<String>();

                            tempList.add(amount);
                            descriptionsSet.add(desc);
                        }
                    }
                    /*if the key = year is not in the map then create new tempFirstMap, a new tempSecondMap, a new set and a new tempList
                    add the amount in the tempList the description in the set */
                    else {
                        tempFirstMap = new HashMap<String, Map<LinkedHashSet, ArrayList<String>>>();
                        tempSecondMap = new HashMap<LinkedHashSet, ArrayList<String>>();
                        descriptionsSet = new LinkedHashSet<>();
                        tempList = new ArrayList<String>();

                        tempList.add(amount);
                        descriptionsSet.add(desc);
                    }
                    /* Store fields in the maps and then in the main map */
                    tempSecondMap.put(desc, tempList);
                    tempFirstMap.put(extractMonthFromDate, tempSecondMap);
                    yearsMappedToMonthsWithAmountsMap.put(extractYearFromDate, tempFirstMap);

                    /* Similarly for the map to store years with months and fileLines that are used in ReportActivity */
                    if (yearsMappedToMonthsWithFileLines.containsKey(extractYearFromDate) && tempFileLinesMap.containsKey(extractMonthFromDate)) {
                        tempFileLinesMap = yearsMappedToMonthsWithFileLines.get(extractYearFromDate);
                        fileLines += line + "\n";

                    }

                    else if (yearsMappedToMonthsWithFileLines.containsKey(extractYearFromDate) && !tempFileLinesMap.containsKey(extractMonthFromDate)) {
                        tempFileLinesMap = yearsMappedToMonthsWithFileLines.get(extractYearFromDate);
                        fileLines = "";
                        fileLines += line + "\n";
                    }

                    else {

                        tempFileLinesMap = new HashMap<String, String>();
                        fileLines = "";
                        fileLines += line + "\n";

                    }

                    tempFileLinesMap.put(extractMonthFromDate, fileLines);
                    yearsMappedToMonthsWithFileLines.put(extractYearFromDate, tempFileLinesMap);

                }
            }
            inputStream.close();

        } catch (FileNotFoundException e) {
            Log.e("File not found ", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException ", e.getMessage());
        }

        iterateMainMap();
        //iterateFileLinesMap();
    }

    private static void addAmountsWithDuplicates(LinkedHashSet descriptions, String desc, String
            amount, ArrayList<Float> arrayAmount) {
        Float amountWithDuplicate;
        if (descriptions.contains(desc)) {
            int i = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); i++) {
                String descFound = (String) s.next();
                if (desc.equals(descFound)) {
                    amountWithDuplicate = arrayAmount.get(i) + Float.valueOf(amount);//add up the
                    // amounts if there are duplicates
                    arrayAmount.set(i, amountWithDuplicate);
                }
            }
        } else {
            descriptions.add(desc);
            arrayAmount.add(Float.valueOf(amount));
        }
    }

    private String iterateFileLinesMap(String monthRequested, String yearRequested){
        String line = "";
        allLinesInFile = "";
        for (Map.Entry<String, Map<String, String>> yearEntry : yearsMappedToMonthsWithFileLines.entrySet()) {
            String year = yearEntry.getKey();
            for ( Map.Entry<String, String> monthEntry : yearEntry.getValue().entrySet()) {
                String month = monthEntry.getKey();
                /*HashMap<String, String> tempMap = (HashMap<String, String>) yearEntry.getValue();
                for (String lineRead : tempMap.values()) {
                    Log.e("Year is" , year);
                    Log.e("File lines ", lineRead);

                    }*/
                allLinesInFile += monthEntry.getValue();
                if (monthRequested.equals(month) && yearRequested.equals(year)) {
                    line += monthEntry.getValue();
                    }
                }
            }
            return line;
        }



    private void iterateMainMap() {
        for (Map.Entry<String, Map<String, Map<String, ArrayList<Float>>>> yearEntry : yearsMappedToMonthsWithAmountsMap.entrySet()) {
            String year = yearEntry.getKey();
            Log.e("Year from file is ", year);
            for (Map.Entry<String, Map<String, ArrayList<Float>>> monthEntry : yearEntry.getValue().entrySet()) {
                String month = monthEntry.getKey();
                Log.e("Month from file is ", month);

                for (Map.Entry<String, ArrayList<Float>> descEntry : monthEntry.getValue().entrySet()) {
                    HashMap<String, ArrayList<Float>> secondMap = (HashMap<String, ArrayList<Float>>) monthEntry.getValue();
                    LinkedHashSet descriptionsSet = new LinkedHashSet<String>(secondMap.keySet());

                        Log.e("Description in file ", descriptionsSet.toString());
                        ArrayList amounts = new ArrayList<Float>();
                        for (ArrayList<Float> listFloats : secondMap.values()) {
                            for(Float amountFloat : listFloats) {
                                amounts.add(amountFloat);
                            }
                        }
                        processAmountsForEveryMonth(month, amounts, objectYear, year, descriptionsSet, iterateFileLinesMap(month, year));
                        for (Object amountInFile : amounts) {
                            Log.e("Amounts from file is ", amountInFile.toString());
                        }
                }
            }
        }
    }

    private void processAmountsForEveryMonth(String month, ArrayList<Float> amounts, AmountsForYear obj, String year, LinkedHashSet set, String fileLine){

        if (month.equals(ONE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {

                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescJan(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(TWO)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescFeb(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(THREE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescMar(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(FOUR)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescApr(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(FIVE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescMay(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(SIX)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescJun(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(SEVEN)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescJul(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(EIGHT)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescAug(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(NINE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescSep(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(TEN)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescOct(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(ELEVEN)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescNov(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        if (month.equals(TWELVE)) {
            Float totalExpensesForMonth = 0.0f;
            for (int i=0; i<amounts.size(); i++) {
                totalExpensesForMonth += amounts.get(i);
            }
            obj.setAmountAndDescDec(totalExpensesForMonth, year, set, amounts, fileLine);
        }
        obj.setAllLinesInFile(allLinesInFile);
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

    public void casesToShowExpensesForMonth(int monthInt, Activity activity) {

        LinkedHashSet test = getObjectYear().getYear2015().getDescriptionsForOct15();
        LinkedHashSet test2 = getObjectYear().getYear2015().getDescriptionsForNov15();
        LinkedHashSet test3 = getObjectYear().getYear2015().getDescriptionsForDec15();
        Log.e("October set is: ", test.toString());
        Log.e("November set is: ", test2.toString());
        Log.e("December set is: ", test3.toString());

        switch (monthInt) {
            case -26:
                showExpensesForMonth(OCTOBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN, getObjectYear().getYear2015().getDescriptionsForOct15(),
                        getObjectYear().getYear2015().getArrayOfamountOct15(), activity);
                break;
            case -25:
                showExpensesForMonth(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN, getObjectYear().getYear2015().getDescriptionsForNov15(),
                        getObjectYear().getYear2015().getArrayOfamountNov15(), activity);
                break;
            case -24:
                showExpensesForMonth(DECEMBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN, getObjectYear().getYear2015().getDescriptionsForDec15(),
                        getObjectYear().getYear2015().getArrayOfamountDec15(), activity);
                break;
            case -23:
                showExpensesForMonth(JANUARY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForJan16(),
                        getObjectYear().getYear2016().getArrayOfamountJan16(), activity);
                break;
            case -22:
                showExpensesForMonth(FEBRUARY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForFeb16(),
                        getObjectYear().getYear2016().getArrayOfamountFeb16(), activity);
                break;
            case -21:
                showExpensesForMonth(MARCH+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForMar16(),
                        getObjectYear().getYear2016().getArrayOfamountMar16(), activity);
                break;
            case -20:
                showExpensesForMonth(APRIL+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForApr16(),
                        getObjectYear().getYear2016().getArrayOfamountApr16(), activity);
                break;
            case -19:
                showExpensesForMonth(MAY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForMay16(),
                        getObjectYear().getYear2016().getArrayOfamountMay16(), activity);
                break;
            case -18:
                showExpensesForMonth(JUNE+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForJun16(),
                        getObjectYear().getYear2016().getArrayOfamountJun16(), activity);
                break;
            case -17:
                showExpensesForMonth(JULY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForJul16(),
                        getObjectYear().getYear2016().getArrayOfamountJul16(), activity);
                break;
            case -16:
                showExpensesForMonth(AUGUST+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForAug16(),
                        getObjectYear().getYear2016().getArrayOfamountAug16(), activity);
                break;
            case -15:
                showExpensesForMonth(SEPTEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForSep16(),
                        getObjectYear().getYear2016().getArrayOfamountSep16(), activity);
                break;
            case -14:
                showExpensesForMonth(OCTOBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForOct16(),
                        getObjectYear().getYear2016().getArrayOfamountOct16(), activity);
                break;
            case -13:
                showExpensesForMonth(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForNov16(),
                        getObjectYear().getYear2016().getArrayOfamountNov16(), activity);
                break;
            case -12:
                showExpensesForMonth(DECEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, getObjectYear().getYear2016().getDescriptionsForDec16(),
                        getObjectYear().getYear2016().getArrayOfamountDec16(), activity);
                break;
            case -11:
                showExpensesForMonth(JANUARY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForJan17(),
                        getObjectYear().getYear2017().getArrayOfamountJan17(), activity);
                break;
            case -10:
                showExpensesForMonth(FEBRUARY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForFeb17(),
                        getObjectYear().getYear2017().getArrayOfamountFeb17(), activity);
                break;
            case -9:
                showExpensesForMonth(MARCH+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForMar17(),
                        getObjectYear().getYear2017().getArrayOfamountMar17(), activity);
                break;
            case -8:
                showExpensesForMonth(APRIL+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForApr17(),
                        getObjectYear().getYear2017().getArrayOfamountApr17(), activity);
                break;
            case -7:
                showExpensesForMonth(MAY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForMay17(),
                        getObjectYear().getYear2017().getArrayOfamountMay17(), activity);
                break;
            case -6:
                showExpensesForMonth(JUNE+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForJun17(),
                        getObjectYear().getYear2017().getArrayOfamountJun17(), activity);
                break;
            case -5:
                showExpensesForMonth(JULY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForJul17(),
                        getObjectYear().getYear2017().getArrayOfamountJul17(), activity);
                break;
            case -4:
                showExpensesForMonth(AUGUST+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForAug17(),
                        getObjectYear().getYear2017().getArrayOfamountAug17(), activity);
                break;
            case -3:
                showExpensesForMonth(SEPTEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForSep17(),
                        getObjectYear().getYear2017().getArrayOfamountSep17(), activity);
                break;
            case -2:
                showExpensesForMonth(OCTOBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForOct17(),
                        getObjectYear().getYear2017().getArrayOfamountOct17(), activity);
                break;
            case -1:
                showExpensesForMonth(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForNov17(),
                        getObjectYear().getYear2017().getArrayOfamountNov17(), activity);
                break;
            case 0:
                showExpensesForMonth(DECEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, getObjectYear().getYear2017().getDescriptionsForDec17(),
                        getObjectYear().getYear2017().getArrayOfamountDec17(), activity);
                break;
            case 1:
                showExpensesForMonth(JANUARY, getObjectYear().getYear2018().getDescriptionsForJan(),
                        getObjectYear().getYear2018().getArrayOfamountJan(), activity);
                break;
            case 2:
                showExpensesForMonth(FEBRUARY, getObjectYear().getYear2018().getDescriptionsForFeb(),
                        getObjectYear().getYear2018().getArrayOfamountFeb(), activity);
                break;
            case 3:
                showExpensesForMonth(MARCH, getObjectYear().getYear2018().getDescriptionsForMar(),
                        getObjectYear().getYear2018().getArrayOfamountMar(), activity);
                break;
            case 4:
                showExpensesForMonth(APRIL, getObjectYear().getYear2018().getDescriptionsForApr(),
                        getObjectYear().getYear2018().getArrayOfamountApr(), activity);
                break;
            case 5:
                showExpensesForMonth(MAY, getObjectYear().getYear2018().getDescriptionsForMay(),
                        getObjectYear().getYear2018().getArrayOfamountMay(), activity);
                break;
            case 6:
                showExpensesForMonth(JUNE, getObjectYear().getYear2018().getDescriptionsForJun(),
                        getObjectYear().getYear2018().getArrayOfamountJun(), activity);
                break;
            case 7:
                showExpensesForMonth(JULY, getObjectYear().getYear2018().getDescriptionsForJul(),
                        getObjectYear().getYear2018().getArrayOfamountJul(), activity);
                break;
            case 8:
                showExpensesForMonth(AUGUST, getObjectYear().getYear2018().getDescriptionsForAug(),
                        getObjectYear().getYear2018().getArrayOfamountAug(), activity);
                break;
            case 9:
                showExpensesForMonth(SEPTEMBER, getObjectYear().getYear2018().getDescriptionsForSep(),
                        getObjectYear().getYear2018().getArrayOfamountSep(), activity);
                break;
            case 10:
                showExpensesForMonth(OCTOBER, getObjectYear().getYear2018().getDescriptionsForOct(),
                        getObjectYear().getYear2018().getArrayOfamountOct(), activity);
                break;
            case 11:
                showExpensesForMonth(NOVEMBER, getObjectYear().getYear2018().getDescriptionsForNov(),
                        getObjectYear().getYear2018().getArrayOfamountNov(), activity);
                break;
            case 12:
                showExpensesForMonth(DECEMBER, getObjectYear().getYear2018().getDescriptionsForDec(),
                        getObjectYear().getYear2018().getArrayOfamountDec(), activity);
                break;
        }// end of switch
    }

    private void showExpensesForMonth(String month, LinkedHashSet descriptions, ArrayList<Float>
            arrayAmount, Activity activity) {

        if (activity instanceof ChartActivity) {

            ((ChartActivity) activity).getMonthLabel().setText(month);
            ((ChartActivity) activity).getDataSet().clear();
            ((ChartActivity) activity).getxVals().clear();
            int x = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); x++) {
                String desc = (String) s.next();
                ((ChartActivity) activity).getyVals1().add(new Entry(arrayAmount.get(x), x));
                ((ChartActivity) activity).getxVals().add(desc);
            }
            ((ChartActivity) activity).setDataSet(new PieDataSet(((ChartActivity) activity).getyVals1(), EXPENSES_FOR + month));
        }
        else if (activity instanceof HorizontalBarChartActivity){
            ((HorizontalBarChartActivity) activity).getMonthLabel().setText(month);
            ((HorizontalBarChartActivity) activity).getBarDataSet1().clear();
            ((HorizontalBarChartActivity) activity).getxAxis().clear();
            int z = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); z++) {
                String desc = (String) s.next();
                ((HorizontalBarChartActivity) activity).getValueSet1().add(new BarEntry(arrayAmount.get(z), z));
                ((HorizontalBarChartActivity) activity).getxAxis().add(desc);
            }
        }

        modifyData(activity);
    }

    private void modifyData(Activity activity) {

        if (activity instanceof ChartActivity) {
            addColors(activity);
            ((ChartActivity) activity).setData(new PieData(((ChartActivity) activity).getxVals(), ((ChartActivity) activity).getDataSet()));

            //this is called every time the user presses the arrows left or right
            if (((ChartActivity) activity).isStateSwitchButton()) {
                ((ChartActivity) activity).getmChart().setUsePercentValues(false);//if switch is ON it shows numbers not percentages
                ((ChartActivity) activity).getData().setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler
                            viewPortHandler) {
                        return Float.toString(v);
                    }
                });
            } else {

                ((ChartActivity) activity).getmChart().setUsePercentValues(true);//if switch is OFF it shows percentages
                ((ChartActivity) activity).getData().setValueFormatter(new PercentFormatter());
            }

            ((ChartActivity) activity).getData().setValueTextSize(12f);//this is the size of the percentages/numbers
            ((ChartActivity) activity).getData().setValueTextColor(Color.GRAY);
            ((ChartActivity) activity).getmChart().setDrawSliceText(false);//this removes the descriptions from every slice

            ((ChartActivity) activity).getmChart().setData(((ChartActivity) activity).getData());
            //undo all highlights
            ((ChartActivity) activity).getmChart().highlightValues(null);
            //update pie chart
            ((ChartActivity) activity).getmChart().invalidate();
        }

        else if (activity instanceof HorizontalBarChartActivity){
            if (((HorizontalBarChartActivity) activity).getValueSet1().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("No data to present")
                        .setMessage("There are no expenses to display on this month.");
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
                ((HorizontalBarChartActivity) activity).getChart().clear();
            } else {
                ((HorizontalBarChartActivity) activity).setBarDataSet1(new BarDataSet(((HorizontalBarChartActivity) activity).getValueSet1(), EXPENSE));
                ((HorizontalBarChartActivity) activity).getBarDataSet1().setColor(Color.rgb(0, 153, 204));

                ((HorizontalBarChartActivity) activity).getDataSets().add(((HorizontalBarChartActivity) activity).getBarDataSet1());

                ((HorizontalBarChartActivity) activity).setData(new BarData(((HorizontalBarChartActivity) activity).getxAxis(), ((HorizontalBarChartActivity) activity).getDataSets()));

                if (((HorizontalBarChartActivity) activity).isStateSwitchButton()) {
                    calculatePercentagesAndModifyYAxis(activity, ((HorizontalBarChartActivity) activity).getDataSets());

                    ((HorizontalBarChartActivity) activity).getData().setValueFormatter(new PercentFormatter());

                } else {
                    revertToNumbersAndModifyYAxis(activity, ((HorizontalBarChartActivity) activity).getDataSets());

                    ((HorizontalBarChartActivity) activity).getData().setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler
                                viewPortHandler) {
                            return Float.toString(v);
                        }
                    });
                }
                ((HorizontalBarChartActivity) activity).getChart().setData(((HorizontalBarChartActivity) activity).getData());
                ((HorizontalBarChartActivity) activity).getChart().setDescription("");
                ((HorizontalBarChartActivity) activity).getChart().animateXY(2000, 2000);
                ((HorizontalBarChartActivity) activity).getChart().invalidate();
            }
        }
    }

    private void addColors(Activity activity) {
        //add many colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        ((ChartActivity) activity).getDataSet().setColors(colors);
    }

    public void calculatePercentagesAndModifyYAxis(Activity activity, ArrayList<BarDataSet> dataSets) {

        switch (((HorizontalBarChartActivity) activity).getMonthInt()) {
            case -26:
                calculatePercentages(getObjectYear().getYear2015().getArrayOfamountOct15(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -25:
                calculatePercentages(getObjectYear().getYear2015().getArrayOfamountNov15(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -24:
                calculatePercentages(getObjectYear().getYear2015().getArrayOfamountDec15(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -23:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountJan16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -22:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountFeb16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -21:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountMar16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -20:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountApr16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -19:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountMay16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -18:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountJun16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -17:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountJul16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -16:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountAug16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -15:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountSep16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -14:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountOct16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -13:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountNov16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -12:
                calculatePercentages(getObjectYear().getYear2016().getArrayOfamountDec16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -11:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountJan17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -10:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountFeb17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -9:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountMar17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -8:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountApr17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -7:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountMay17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -6:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountJun17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -5:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountJul17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -4:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountAug17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -3:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountSep17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -2:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountOct17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -1:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountNov17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 0:
                calculatePercentages(getObjectYear().getYear2017().getArrayOfamountDec17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 1:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountJan(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 2:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountFeb(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 3:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountMar(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 4:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountApr(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 5:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountMay(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 6:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountJun(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 7:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountJul(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 8:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountAug(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 9:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountSep(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 10:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountOct(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 11:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountNov(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 12:
                calculatePercentages(getObjectYear().getYear2018().getArrayOfamountDec(), activity);
                modifyYAxis(activity, dataSets);
                break;
        }
    }

    private void modifyYAxis(Activity activity, ArrayList<BarDataSet> dataSets) {
        ((HorizontalBarChartActivity) activity).setData(new BarData(((HorizontalBarChartActivity) activity).getxAxis(), dataSets));
        if (dataSets != null) {
            ((HorizontalBarChartActivity) activity).getChart().setData(((HorizontalBarChartActivity) activity).getData());
            ((HorizontalBarChartActivity) activity).getChart().animateXY(2000, 2000);
            ((HorizontalBarChartActivity) activity).getChart().invalidate();
        }
    }

    private void calculatePercentages(ArrayList<Float> arrayOfamount, Activity activity) {

        int total = 0;
        double percentage = 0;
        for (int i = 0; i < arrayOfamount.size(); i++) {
            total += Math.round(arrayOfamount.get(i));
        }
        for (int j = 0; j < arrayOfamount.size(); j++) {
            percentage = Math.round((arrayOfamount.get(j) * 100.0) / (double) total);
            float percentageInFloat = (float) percentage;
            //i replace all the items on the valueSet1 with percentages
            ((HorizontalBarChartActivity) activity).getValueSet1().set(j, new BarEntry(percentageInFloat, j));
        }
    }

    public void revertToNumbersAndModifyYAxis(Activity activity, ArrayList<BarDataSet> dataSets) {

        switch (((HorizontalBarChartActivity) activity).getMonthInt()) {
            case -26:
                revertPercentagesToNumbers(getObjectYear().getYear2015().getArrayOfamountOct15(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -25:
                revertPercentagesToNumbers(getObjectYear().getYear2015().getArrayOfamountNov15(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -24:
                revertPercentagesToNumbers(getObjectYear().getYear2015().getArrayOfamountDec15(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -23:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountJan16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -22:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountFeb16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -21:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountMar16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -20:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountApr16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -19:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountMay16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -18:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountJun16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -17:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountJul16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -16:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountAug16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -15:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountSep16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -14:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountOct16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -13:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountNov16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -12:
                revertPercentagesToNumbers(getObjectYear().getYear2016().getArrayOfamountDec16(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -11:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountJan17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -10:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountFeb17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -9:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountMar17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -8:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountApr17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -7:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountMay17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -6:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountJun17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -5:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountJul17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -4:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountAug17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -3:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountSep17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -2:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountOct17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case -1:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountNov17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 0:
                revertPercentagesToNumbers(getObjectYear().getYear2017().getArrayOfamountDec17(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 1:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountJan(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 2:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountFeb(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 3:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountMar(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 4:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountApr(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 5:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountMay(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 6:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountJun(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 7:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountJul(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 8:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountAug(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 9:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountSep(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 10:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountOct(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 11:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountNov(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 12:
                revertPercentagesToNumbers(getObjectYear().getYear2018().getArrayOfamountDec(), activity);
                modifyYAxis(activity, dataSets);
                break;
        }
    }

    private void revertPercentagesToNumbers(ArrayList<Float> arrayOfamount, Activity activity) {

        for (int j = 0; j < arrayOfamount.size(); j++) {
            ((HorizontalBarChartActivity) activity).getValueSet1().set(j, new BarEntry(arrayOfamount.get(j), j));//i replace all the items
            // with actual numbers as they used to be
        }
    }

    public void calculateSelectedExpenses(Spinner expensesList, Spinner yearList, Activity activity) {

        String selectedExpense = expensesList.getSelectedItem().toString();

        if (yearList.getSelectedItem().equals(TWOTHOUSANDEIGHTEEN)) {

            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForJan(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountJan(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForFeb(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountFeb(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForMar(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountMar(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForApr(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountApr(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForMay(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountMay(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForJun(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountJun(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForJul(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountJul(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForAug(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountAug(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForSep(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountSep(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForOct(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountOct(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForNov(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountNov(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2018().getDescriptionsForDec(), selectedExpense,
                    getObjectYear().getYear2018().getArrayOfamountDec(), activity);
        }

        if (yearList.getSelectedItem().equals(TWOTHOUSANDSEVENTEEN)) {

            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForJan17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountJan17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForFeb17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountFeb17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForMar17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountMar17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForApr17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountApr17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForMay17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountMay17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForJun17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountJun17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForJul17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountJul17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForAug17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountAug17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForSep17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountSep17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForOct17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountOct17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForNov17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountNov17(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2017().getDescriptionsForDec17(), selectedExpense,
                    getObjectYear().getYear2017().getArrayOfamountDec17(), activity);
        } else if (yearList.getSelectedItem().equals(TWOTHOUSANDSIXTEEN)) {

            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForJan16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountJan16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForFeb16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountFeb16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForMar16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountMar16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForApr16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountApr16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForMay16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountMay16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForJun16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountJun16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForJul16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountJul16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForAug16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountAug16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForSep16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountSep16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForOct16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountOct16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForNov16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountNov16(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2016().getDescriptionsForDec16(), selectedExpense,
                    getObjectYear().getYear2016().getArrayOfamountDec16(), activity);
        } else if (yearList.getSelectedItem().equals(TWOTHOUSANDFIFTEEN)) {

            calculateExpensesByMonth(getObjectYear().getYear2015().getDescriptionsForOct15(), selectedExpense,
                    getObjectYear().getYear2015().getArrayOfamountOct15(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2015().getDescriptionsForNov15(), selectedExpense,
                    getObjectYear().getYear2015().getArrayOfamountNov15(), activity);
            calculateExpensesByMonth(getObjectYear().getYear2015().getDescriptionsForDec15(), selectedExpense,
                    getObjectYear().getYear2015().getArrayOfamountDec15(), activity);

        }
    }

    private void calculateExpensesByMonth(LinkedHashSet descriptions, String selectedExpense,
                                          ArrayList<Float> arrayAmount, Activity activity) {

        Iterator Itr = descriptions.iterator();
        int i = 0;
        while (Itr.hasNext()) {
            String descFound = Itr.next().toString();
            if (selectedExpense.equals(descFound)) {
                ((CalculateAnnualExpensesActivity)activity).setAnnualExpenseDouble(((CalculateAnnualExpensesActivity)activity).getAnnualExpenseDouble() + (double) arrayAmount.get(i));
            }
            i++;
        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public void readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle(int salaryDay,
                                                                             String currentMonth, AmountsFor2018 obj2018)
            throws ParseException {

        String fileLine = "";
        String amount = "";
        String date = "";

        try {
            InputStream inputStream = new FileInputStream("/data/data/uk.co.irokottaki" +
                    ".moneycontrol/files/expenses.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            int lineIndex = 0;//this is to count the lines
            while ((line = br.readLine()) != null) {

                if (lineIndex == 0) {
                    fileLine = line + "\n" + "\n";// need to catch the space and write only one
                    // space after the header.
                }

                if (++lineIndex > 2 && !line.equals("") && !line.equals("r")) {
                    fileLine += line + "\n";
                    int index = line.lastIndexOf(" ");
                    amount = line.substring(0, line.indexOf(" "));
                    date = line.substring(index, line.length());
                    String extractDayFromDate = date.substring(0, date.indexOf("/"));
                    //convert String to int but first take the second character e.g. take 5 from 05
                    int extractDayFromDateInt = Integer.parseInt(extractDayFromDate.trim()
                            .replaceFirst("^0+(?!$)", ""));
                    String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date
                            .lastIndexOf("/"));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date
                            .length());

                    final Calendar calendar = Calendar.getInstance();
                    java.util.Date currentDate = new SimpleDateFormat("MMM", Locale.ENGLISH)
                            .parse(currentMonth);
                    calendar.setTime(currentDate);// here i convert the String month in an
                    // integer to be used on the switch-case
                    int monthInt = calendar.get(Calendar.MONTH) + 1;
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    if (salaryDay != 0 && currentMonth != null) {
                        if (salaryDay <= extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt - 1)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {
                            /*if the salary day set by the user is before the day of the expense
                            made and the expense made in the
                            previous month then the expense is added in the current month*/
                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt, obj2018);
                        }
                            /*else if the salary day set by the user is after the day of the
                            expense made and the expense made
                            on the current month then the expense amount is added in the current
                            month*/
                        else if (salaryDay > extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {

                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt,obj2018);
                        } else if (salaryDay <= extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {
                            /*if the day set by the user is before the day of the expense made
                            and the expense made in the
                            current month then the expense is added in the next month*/
                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt + 1,obj2018);
                        } else if (salaryDay > extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt - 1)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {
                            /*if the day set by the user is after the day of the expense made and
                             the expense made in the
                            previous month then the expense is added in the previous month*/
                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt - 1,obj2018);

                        }
                    }
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("File not found",e.getMessage());
        } catch (IOException e) {
            Log.e("IOException",e.getMessage());
        }
    }

    private void addAmountToCurrentOrNextMonth(Float amount, int monthInt, AmountsFor2018 obj2018) {

        switch (monthInt) {

            case 1:
                obj2018.setAmountJan(obj2018.getAmountJan()+amount);
                break;
            case 2:
                obj2018.setAmountFeb(obj2018.getAmountFeb()+amount);
                break;
            case 3:
                obj2018.setAmountMar(obj2018.getAmountMar()+amount);
                break;
            case 4:
                obj2018.setAmountApr(obj2018.getAmountApr()+amount);
                break;
            case 5:
                obj2018.setAmountMay(obj2018.getAmountMay()+amount);
                break;
            case 6:
                obj2018.setAmountJun(obj2018.getAmountJun()+amount);
                break;
            case 7:
                obj2018.setAmountJul(obj2018.getAmountJul()+amount);
                break;
            case 8:
                obj2018.setAmountAug(obj2018.getAmountAug()+amount);
                break;
            case 9:
                obj2018.setAmountSep(obj2018.getAmountSep()+amount);
                break;
            case 10:
                obj2018.setAmountOct(obj2018.getAmountOct()+amount);
                break;
            case 11:
                obj2018.setAmountNov(obj2018.getAmountNov()+amount);
                break;
            case 12:
                obj2018.setAmountDec(obj2018.getAmountDec()+amount);
                break;
        }

    }

}
